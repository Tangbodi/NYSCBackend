package com.example.demo.Service.ClientsFundersService;

import com.example.demo.Model.DTO.ClientFundersDTO;
import com.example.demo.Model.Entity.ClientFunders;
import com.example.demo.Model.Entity.ClientFunderId;
import com.example.demo.Model.VO.ClientFundersVO;
import com.example.demo.Model.VO.ClientServicesVO;
import com.example.demo.Model.VO.ServiceLinesVO;
import com.example.demo.Repository.ClientFundersRepository;
import com.example.demo.Util.DateTimeConverter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class ClientFundersService {
    private static final Logger logger = LoggerFactory.getLogger(ClientFundersService.class);

    @Autowired
    private ClientFundersRepository clientFundersRepository;

    @Transactional
    public void CreateClientsFunders(ClientFundersDTO dto) {
        logger.info("Creating ClientsFunders for clientId: {}, funderId: {}", dto.getClientId(), dto.getFunderId());
        try {
            ClientFunderId key = new ClientFunderId();
            key.setClientId(Long.valueOf(dto.getClientId()));
            key.setFunderId(Integer.valueOf(dto.getFunderId()));

            ClientFunders clientFunders = new ClientFunders();
            clientFunders.setId(key);
            clientFunders.setInsuranceId(dto.getInsuranceId());
            clientFunders.setRelationship(dto.getRelationship());
            clientFunders.setStartDate(dto.getStartDate());
            clientFunders.setEndDate(dto.getEndDate());
            clientFunders.setFirstName(dto.getFirstName());
            clientFunders.setLastName(dto.getLastName());
            clientFunders.setCoverageType(dto.getCoverageType());
            clientFunders.setCreatedAt(Instant.now());
            clientFunders.setModifiedAt(Instant.now());
            clientFundersRepository.save(clientFunders);
            logger.info("ClientsFunders created successfully.");
        } catch (Exception e) {
            logger.error("Failed to create ClientsFunders: {}", e.getMessage(), e);
            throw e;
        }
    }

    public ClientFundersVO GetClientsFunders(String clientId, String funderId) {
        logger.info("Getting ClientsFunders for clientId: {}, funderId: {}", clientId, funderId);
        try {
            ClientFunderId key = new ClientFunderId();
            key.setClientId(Long.valueOf(clientId));
            key.setFunderId(Integer.valueOf(funderId));

            ClientFunders clientFunders = clientFundersRepository.findById(key).orElse(null);
            if (clientFunders != null) {
                logger.info("Found ClientsFunders.");
                return ConvertToClientsFundersVO(clientFunders);
            } else {
                logger.info("No ClientsFunders found.");
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to find ClientsFunders: {}", e.getMessage(), e);
        }
        return null;
    }

    public List<ClientFundersVO> GetAllFundersByClient(String clientId) {
        logger.info("Getting all funders by clientId: {}", clientId);
        try {
            List<Map<String, Object>> fundersList = clientFundersRepository.findFundersByClientId(Long.valueOf(clientId));
            if (!fundersList.isEmpty()) {
                logger.info("Found fundersList.");
                List<ClientFundersVO> clientFundersVOList = new ArrayList<>();
                for (Map<String, Object> row : fundersList) {
                    ClientFundersVO vo = new ClientFundersVO();

                    // client_funders
                    vo.setClientId(nullSafe(row.get("client_id")));
                    vo.setFunderId(nullSafe(row.get("funder_id")));
                    vo.setInsuranceId(nullSafe(row.get("insurance_id")));
                    vo.setRelationship(nullSafe(row.get("relationship")));
                    vo.setStartDate(nullSafe(row.get("start_date")));
                    vo.setEndDate(nullSafe(row.get("end_date")));
                    vo.setFirstName(nullSafe(row.get("first_name")));
                    vo.setLastName(nullSafe(row.get("last_name")));
                    vo.setCoverageType(nullSafe(row.get("coverage_type")));

                    // clients_info
                    vo.setClientFirstName(nullSafe(row.get("client_first_name")));
                    vo.setClientLastName(nullSafe(row.get("client_last_name")));
                    vo.setClientMiddleName(nullSafe(row.get("client_middle_name")));
                    vo.setDateOfBirth(nullSafe(row.get("date_of_birth")));
                    vo.setGender(nullSafe(row.get("gender")));
                    vo.setStatus(nullSafe(row.get("status")));

                    // funder_settings
                    vo.setFunderType(nullSafe(row.get("funder_type")));
                    vo.setFunderName(nullSafe(row.get("funder_name")));
                    vo.setFunderAddress(nullSafe(row.get("funder_address")));
                    vo.setCoverageType(nullSafe(row.get("coverage_type")));
                    vo.setVendorId(nullSafe(row.get("vendor_id")));
                    vo.setPhone(nullSafe(row.get("phone")));
                    vo.setEmail(nullSafe(row.get("email")));
                    vo.setFax(nullSafe(row.get("fax")));
                    vo.setDefaultBillingProvider(nullSafe(row.get("default_billing_provider")));

                    vo.setCreatedAt(nullSafe(row.get("created_at")));
                    vo.setModifiedAt(nullSafe(row.get("modified_at")));

                    clientFundersVOList.add(vo);
                }
                return clientFundersVOList;
            } else {
                logger.info("No fundersList found.");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            logger.error("Failed to get fundersList: {}", e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Transactional
    public void UpdateClientsFunders(ClientFundersDTO dto) {
        logger.info("Updating ClientsFunders clientId: {}, funderId: {}", dto.getClientId(), dto.getFunderId());
        try {
            clientFundersRepository.UpdateClientsFunderByClientIdAndFunderId(
                    Long.valueOf(dto.getClientId()),
                    Integer.valueOf(dto.getFunderId()),
                    dto.getInsuranceId(),
                    dto.getRelationship(),
                    dto.getStartDate(),
                    dto.getEndDate(),
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getCoverageType()
            );
            logger.info("ClientsFunders updated successfully.");
        } catch (Exception e) {
            logger.error("Failed to update ClientsFunders: {}", e.getMessage(), e);
            throw e;
        }
    }

    public ClientServicesVO GetAllServicesByClient(String clientId) {
        logger.info("Getting all services for client: {}", clientId);
        try {
            List<Map<String, Object>> rows = clientFundersRepository.findServicesByClientId(Long.valueOf(clientId));
            ClientServicesVO vo = new ClientServicesVO();
            vo.setClientId(clientId);
            List<ServiceLinesVO> serviceList = new ArrayList<>();
            for (Map<String, Object> row : rows) {
                ServiceLinesVO serviceVO = new ServiceLinesVO();
                serviceVO.setServiceId(nullSafe(row.get("service_id")));
                serviceVO.setBillingCode(nullSafe(row.get("billing_code")));
                serviceVO.setRatePerUnit(nullSafe(row.get("rate_per_unit")));
                serviceVO.setUnitType(nullSafe(row.get("unit_type")));
                serviceVO.setService(nullSafe(row.get("service")));
                serviceVO.setDescription(nullSafe(row.get("description")));
                serviceVO.setInactive(nullSafe(row.get("inactive")));
                serviceVO.setCreatedAt(nullSafe(row.get("created_at")));
                serviceVO.setModifiedAt(nullSafe(row.get("modified_at")));
                serviceList.add(serviceVO);
            }
            vo.setServices(serviceList);
            return vo;
        } catch (Exception e) {
            logger.error("Failed to get services for client: {}", e.getMessage(), e);
        }
        return null;
    }

    @Transactional
    public void DeleteClientsFunders(String clientId, String funderId) {
        logger.info("Deleting ClientsFunders clientId: {}, funderId: {}", clientId, funderId);
        try {
            clientFundersRepository.deleteByClientIdAndFunderId(
                    Long.valueOf(clientId),
                    Integer.valueOf(funderId)
            );
            logger.info("ClientsFunders deleted successfully.");
        } catch (Exception e) {
            logger.error("Failed to delete ClientsFunders: {}", e.getMessage(), e);
            throw e;
        }
    }

    public ClientFundersVO ConvertToClientsFundersVO(ClientFunders clientFunders) {
        ClientFundersVO vo = new ClientFundersVO();
        vo.setClientId(String.valueOf(clientFunders.getId().getClientId()));
        vo.setFunderId(String.valueOf(clientFunders.getId().getFunderId()));
        vo.setInsuranceId(clientFunders.getInsuranceId());
        vo.setRelationship(clientFunders.getRelationship());
        vo.setStartDate(clientFunders.getStartDate());
        vo.setEndDate(clientFunders.getEndDate());
        vo.setFirstName(clientFunders.getFirstName());
        vo.setLastName(clientFunders.getLastName());
        vo.setCoverageType(clientFunders.getCoverageType());
        vo.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(clientFunders.getCreatedAt()));
        vo.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(clientFunders.getModifiedAt()));
        return vo;
    }

    private String nullSafe(Object value) {
        return value == null ? "" : String.valueOf(value);
    }
}
