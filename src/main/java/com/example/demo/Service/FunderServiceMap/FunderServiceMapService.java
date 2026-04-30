package com.example.demo.Service.FunderServiceMap;

import com.example.demo.Model.DTO.FunderServiceMapDTO;
import com.example.demo.Model.Entity.FunderServiceId;
import com.example.demo.Model.Entity.FunderServiceMap;
import com.example.demo.Model.Entity.ServiceLines;
import com.example.demo.Model.VO.AssignedServiceVO;
import com.example.demo.Model.VO.FunderServiceMapVO;
import com.example.demo.Repository.FunderServiceMapRepository;
import com.example.demo.Repository.ServiceLinesRepository;
import com.example.demo.Util.DateTimeConverter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class FunderServiceMapService {
    private static final Logger logger = LoggerFactory.getLogger(FunderServiceMapService.class);

    @Autowired
    private FunderServiceMapRepository funderServiceMapRepository;
    @Autowired
    private ServiceLinesRepository serviceLinesRepository;

    @Transactional
    public void AddServiceToFunder(FunderServiceMapDTO dto) {
        logger.info("Adding service {} to funder {}", dto.getServiceId(), dto.getFunderId());
        try {
            FunderServiceId compositeId = buildId(dto);

            if (funderServiceMapRepository.existsById(compositeId)) {
                throw new RuntimeException("Service " + dto.getServiceId() + " is already mapped to funder " + dto.getFunderId() + ".");
            }

            FunderServiceMap map = new FunderServiceMap();
            map.setId(compositeId);
            map.setCreatedAt(Instant.now());
            map.setModifiedAt(Instant.now());
            funderServiceMapRepository.save(map);
            logger.info("Service {} added to funder {} successfully.", dto.getServiceId(), dto.getFunderId());
        } catch (Exception e) {
            logger.error("Failed to add service to funder: {}", e.getMessage(), e);
            throw e;
        }
    }

    public FunderServiceMapVO GetServicesByFunder(String funderId) {
        logger.info("Getting all services for funder: {}", funderId);
        try {
            List<FunderServiceMap> mappings = funderServiceMapRepository.findByIdFunderId(Integer.valueOf(funderId));

            FunderServiceMapVO vo = new FunderServiceMapVO();
            vo.setFunderId(funderId);

            List<AssignedServiceVO> serviceList = new ArrayList<>();
            for (FunderServiceMap mapping : mappings) {
                ServiceLines serviceLine = serviceLinesRepository
                        .findById(mapping.getId().getServiceId()).orElse(null);
                if (serviceLine != null) {
                    serviceList.add(ConvertToAssignedServiceVO(serviceLine, mapping));
                } else {
                    logger.warn("ServiceLine {} not found for mapping.", mapping.getId().getServiceId());
                }
            }
            vo.setServices(serviceList);
            return vo;
        } catch (Exception e) {
            logger.error("Failed to get services for funder: {}", e.getMessage(), e);
        }
        return null;
    }

    @Transactional
    public void RemoveServiceFromFunder(FunderServiceMapDTO dto) {
        logger.info("Removing service {} from funder {}", dto.getServiceId(), dto.getFunderId());
        try {
            FunderServiceId compositeId = buildId(dto);

            if (!funderServiceMapRepository.existsById(compositeId)) {
                throw new RuntimeException("No mapping found for funder " + dto.getFunderId() + " and service " + dto.getServiceId() + ".");
            }
            funderServiceMapRepository.deleteById(compositeId);
            logger.info("Service {} removed from funder {} successfully.", dto.getServiceId(), dto.getFunderId());
        } catch (Exception e) {
            logger.error("Failed to remove service from funder: {}", e.getMessage(), e);
            throw e;
        }
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private FunderServiceId buildId(FunderServiceMapDTO dto) {
        FunderServiceId id = new FunderServiceId();
        id.setFunderId(Integer.valueOf(dto.getFunderId()));
        id.setServiceId(Integer.valueOf(dto.getServiceId()));
        return id;
    }

    private AssignedServiceVO ConvertToAssignedServiceVO(ServiceLines serviceLine, FunderServiceMap mapping) {
        AssignedServiceVO vo = new AssignedServiceVO();
        vo.setServiceId(String.valueOf(serviceLine.getId()));
        vo.setBillingCode(serviceLine.getBillingCode());
        vo.setRatePerUnit(serviceLine.getRatePerUnit());
        vo.setUnitType(serviceLine.getUnitType());
        vo.setService(serviceLine.getService());
        vo.setDescription(serviceLine.getDescription());
        vo.setInactive(serviceLine.getInactive());
        vo.setAssignedAt(DateTimeConverter.DateTimeConvertFromInstant(mapping.getCreatedAt()));
        return vo;
    }
}
