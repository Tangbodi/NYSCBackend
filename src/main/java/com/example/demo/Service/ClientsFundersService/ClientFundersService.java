package com.example.demo.Service.ClientsFundersService;

import com.example.demo.Model.DTO.ClientFundersDTO;
import com.example.demo.Model.Entity.ClientFunders;
import com.example.demo.Model.VO.ClientFundersVO;
import com.example.demo.Model.VO.ClientServicesVO;
import com.example.demo.Model.VO.ServiceLinesVO;
import com.example.demo.Repository.ClientFundersRepository;
import com.example.demo.Util.DateTimeConverter;
import com.example.demo.Util.Snowflake;
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
    public void CreateClientsFunders(ClientFundersDTO clientFundersDTO){
        logger.info("Creating ClientsFunders: {}");
        try{
            ClientFunders clientFunders = new ClientFunders();

            clientFunders.setClientId(Long.valueOf(clientFundersDTO.getClientId()));
            clientFunders.setFunderId(Integer.valueOf(clientFundersDTO.getFunderId()));
            clientFunders.setInsuranceId(clientFundersDTO.getInsuranceId());
            clientFunders.setCreatedAt(Instant.now());
            clientFunders.setModifiedAt(Instant.now());
            clientFundersRepository.save(clientFunders);
            logger.info("ClientsFunders created successfully.");

        }catch (Exception e) {
            logger.error("Failed to create ClientsFunders: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ClientFundersVO> GetAllFundersByClient(String clientId){
        logger.info("Getting all funders by clientId: {}", clientId);
        try{
            List<Map<String,Object>> fundersList = clientFundersRepository.findFundersByClientId(Long.valueOf(clientId));
            if (!fundersList.isEmpty()) {
                logger.info("Found fundersList.");
                List<ClientFundersVO> clientFundersVOList = new ArrayList<>();
                for(Map<String,Object> row : fundersList){
                    ClientFundersVO vo = new ClientFundersVO();

                    // client_funders
                    vo.setId(nullSafe(row.get("id")));
                    vo.setClientId(nullSafe(row.get("client_id")));
                    vo.setFunderId(nullSafe(row.get("funder_id")));
                    vo.setInsuranceId(nullSafe(row.get("insurance_id")));

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
            }else{
                logger.info("No fundersList found.");
                return Collections.emptyList();
            }
        }catch (Exception e) {
            logger.error("Failed to get fundersList: {}", e.getMessage(), e);
        }
        return Collections.emptyList();
    }
    public ClientFundersVO GetClientsFunders(String funderId){
        logger.info("Getting ClientsFunders: {}", funderId);
        try{
            ClientFunders clientFunders = clientFundersRepository.findById(Long.valueOf(funderId)).orElse(null);
            if(clientFunders != null){
                logger.info("Found ClientsFunders.");
                return ConvertToClientsFundersVO(clientFunders);
            }else{
                logger.info("No ClientsFunders found.");
                return null;
            }
        }catch (Exception e) {
            logger.error("Failed to find ClientsFunders: {}", e.getMessage(), e);
        }
        return null;
    }
    @Transactional
    public void UpdateClientsFunders(ClientFundersDTO clientFundersDTO){
        logger.info("Updating ClientsFunders: {}", "Funder ID:"+ clientFundersDTO.getFunderId(),"Client ID:"+ clientFundersDTO.getClientId());
        try{
            clientFundersRepository.UpdateClientsFunderByClientIdAndFunderId(
                    Long.valueOf(clientFundersDTO.getClientId()),
                    Integer.valueOf(clientFundersDTO.getFunderId()),
                    clientFundersDTO.getInsuranceId()
                    );
            logger.info("ClientsFunders updated successfully.");
        }catch (Exception e) {
            logger.error("Failed to update ClientsFunders: {}", e.getMessage(), e);
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
    public void DeleteClientsFunders(String id) {
        logger.info("Deleting ClientsFunders: {}", id);
        try {
            Long lid = Long.valueOf(id);
            if (!clientFundersRepository.existsById(lid)) {
                throw new RuntimeException("Client funder not found for id: " + id);
            }
            clientFundersRepository.deleteById(lid);
            logger.info("ClientsFunders deleted successfully.");
        } catch (Exception e) {
            logger.error("Failed to delete ClientsFunders: {}", e.getMessage(), e);
            throw e;
        }
    }

    private String nullSafe(Object value) {
        return value == null ? "" : String.valueOf(value);
    }

    public ClientFundersVO ConvertToClientsFundersVO(ClientFunders clientFunders){
        logger.info("Converting to ClientsFundersVO: {}", clientFunders.getId());
        ClientFundersVO clientFundersVO = new ClientFundersVO();
        clientFundersVO.setFunderId(String.valueOf(clientFunders.getId()));
        clientFundersVO.setClientId(String.valueOf(clientFunders.getClientId()));
        clientFundersVO.setInsuranceId(clientFundersVO.getInsuranceId());
        String formattedCreatedDateTime = DateTimeConverter.DateTimeConvertFromInstant(clientFunders.getCreatedAt());
        String formattedModifiedDateTime = DateTimeConverter.DateTimeConvertFromInstant(clientFunders.getModifiedAt());
        clientFundersVO.setCreatedAt(formattedCreatedDateTime);
        clientFundersVO.setModifiedAt(formattedModifiedDateTime);
        logger.info("ClientsFundersVO converted successfully.");
        return clientFundersVO;
    }
}
