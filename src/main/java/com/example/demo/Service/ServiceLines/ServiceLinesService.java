package com.example.demo.Service.ServiceLines;

import com.example.demo.Model.DTO.ServiceLinesDTO;
import com.example.demo.Model.Entity.ServiceLines;
import com.example.demo.Model.VO.ServiceLinesVO;
import com.example.demo.Repository.ServiceLinesRepository;
import com.example.demo.Util.DateTimeConverter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ServiceLinesService {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLinesService.class);

    @Autowired
    private ServiceLinesRepository serviceLinesRepository;

    @Transactional
    public void CreateServiceLine(ServiceLinesDTO dto) {
        logger.info("Creating ServiceLine: {}", dto.getService());
        try {
            ServiceLines serviceLine = new ServiceLines();
            serviceLine.setBillingCode(dto.getBillingCode());
            serviceLine.setRatePerUnit(dto.getRatePerUnit());
            serviceLine.setUnitType(dto.getUnitType());
            serviceLine.setService(dto.getService());
            serviceLine.setDescription(dto.getDescription());
            serviceLine.setInactive(dto.getInactive() == null ? "0" : dto.getInactive());
            serviceLine.setStartDate(dto.getStartDate());
            serviceLine.setEndDate(dto.getEndDate());
            serviceLine.setCreatedAt(DateTimeConverter.nowNyc());
            serviceLine.setModifiedAt(DateTimeConverter.nowNyc());
            serviceLinesRepository.save(serviceLine);
            logger.info("ServiceLine created successfully.");
        } catch (Exception e) {
            logger.error("Failed to create ServiceLine: {}", e.getMessage(), e);
            throw e;
        }
    }

    public ServiceLinesVO GetServiceLine(String serviceId) {
        logger.info("Getting ServiceLine: {}", serviceId);
        try {
            ServiceLines serviceLine = serviceLinesRepository.findById(Integer.valueOf(serviceId)).orElse(null);
            if (serviceLine != null) {
                return ConvertToVO(serviceLine);
            } else {
                logger.info("ServiceLine not found: {}", serviceId);
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to get ServiceLine: {}", e.getMessage(), e);
        }
        return null;
    }

    public List<ServiceLinesVO> GetAllServiceLines() {
        logger.info("Getting all ServiceLines.");
        try {
            List<ServiceLines> serviceLines = serviceLinesRepository.findAll();
            if (!serviceLines.isEmpty()) {
                List<ServiceLinesVO> voList = new ArrayList<>();
                for (ServiceLines serviceLine : serviceLines) {
                    voList.add(ConvertToVO(serviceLine));
                }
                return voList;
            } else {
                logger.info("No ServiceLines found.");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            logger.error("Failed to get all ServiceLines: {}", e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Transactional
    public void UpdateServiceLine(String serviceId, ServiceLinesDTO dto) {
        logger.info("Updating ServiceLine: {}", serviceId);
        try {
            Instant modifiedAt = DateTimeConverter.nowNyc();
            serviceLinesRepository.UpdateServiceLine(
                    Integer.valueOf(serviceId),
                    dto.getBillingCode(),
                    dto.getRatePerUnit(),
                    dto.getUnitType(),
                    dto.getService(),
                    dto.getDescription(),
                    dto.getInactive() == null ? "0" : dto.getInactive(),
                    dto.getStartDate(),
                    dto.getEndDate(),
                    modifiedAt
            );
            logger.info("ServiceLine updated successfully.");
        } catch (Exception e) {
            logger.error("Failed to update ServiceLine: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void DeleteServiceLine(String serviceId) {
        logger.info("Deleting ServiceLine: {}", serviceId);
        try {
            Integer id = Integer.valueOf(serviceId);
            if (!serviceLinesRepository.existsById(id)) {
                throw new RuntimeException("Service line not found for id: " + serviceId);
            }
            serviceLinesRepository.deleteById(id);
            logger.info("ServiceLine deleted successfully.");
        } catch (Exception e) {
            logger.error("Failed to delete ServiceLine: {}", e.getMessage(), e);
            throw e;
        }
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private ServiceLinesVO ConvertToVO(ServiceLines serviceLine) {
        ServiceLinesVO vo = new ServiceLinesVO();
        vo.setServiceId(String.valueOf(serviceLine.getId()));
        vo.setBillingCode(serviceLine.getBillingCode());
        vo.setRatePerUnit(serviceLine.getRatePerUnit());
        vo.setUnitType(serviceLine.getUnitType());
        vo.setService(serviceLine.getService());
        vo.setDescription(serviceLine.getDescription());
        vo.setInactive(serviceLine.getInactive());
        vo.setStartDate(serviceLine.getStartDate());
        vo.setEndDate(serviceLine.getEndDate());
        vo.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(serviceLine.getCreatedAt()));
        vo.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(serviceLine.getModifiedAt()));
        return vo;
    }
}
