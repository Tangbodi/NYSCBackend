package com.example.demo.Service.ClientsFundersService;

import com.example.demo.Model.DTO.ClientsFundersDTO;
import com.example.demo.Model.Entity.ClientsFunders;
import com.example.demo.Model.VO.ClientsFundersVO;
import com.example.demo.Repository.ClientsFundersRepository;
import com.example.demo.Service.ClientsContacts.ClientsContactsService;
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
public class ClientsFundersService {
    private static final Logger logger = LoggerFactory.getLogger(ClientsFundersService.class);
    @Autowired
    private ClientsFundersRepository clientsFundersRepository;

    @Transactional
    public void CreateClientsFunders(ClientsFundersDTO clientsFundersDTO){
        logger.info("Creating ClientsFunders: {}");
        try{
            ClientsFunders clientsFunders = new ClientsFunders();
            Long snowflakeId = Snowflake.generateUniqueId();
            clientsFunders.setId(snowflakeId);
            clientsFunders.setClientId(Long.valueOf(clientsFundersDTO.getClientId()));
            clientsFunders.setPayerName(clientsFundersDTO.getPayerName());
            clientsFunders.setPlanName(clientsFundersDTO.getPlanName());
            clientsFunders.setMemberId(clientsFundersDTO.getMemberId());
            clientsFunders.setGroupNumber(clientsFundersDTO.getGroupNumber());
            clientsFunders.setRelationshipToClient(clientsFundersDTO.getRelationshipToClient());
            clientsFunders.setPolicyHolderName(clientsFundersDTO.getPolicyHolderName());
            clientsFunders.setPolicyHolderPhone(clientsFundersDTO.getPolicyHolderPhone());
            clientsFunders.setPolicyHolderEmail(clientsFundersDTO.getPolicyHolderEmail());
            clientsFunders.setPolicyHolderAddress(clientsFundersDTO.getPolicyHolderAddress());
            clientsFunders.setPolicyHolderCity(clientsFundersDTO.getPolicyHolderCity());
            clientsFunders.setPolicyHolderState(clientsFundersDTO.getPolicyHolderState());
            clientsFunders.setPolicyHolderZipCode(clientsFundersDTO.getPolicyHolderZipCode());
            clientsFunders.setCoverageOrder(clientsFundersDTO.getCoverageOrder());
            clientsFunders.setEffectiveStart(clientsFundersDTO.getEffectiveStart());
            clientsFunders.setEffectiveEnd(clientsFundersDTO.getEffectiveEnd());
            clientsFunders.setIsActive(clientsFundersDTO.getIsActive());
            clientsFunders.setNotes(clientsFundersDTO.getNotes());
            clientsFunders.setCreatedAt(Instant.now());
            clientsFunders.setModifiedAt(Instant.now());
            clientsFundersRepository.save(clientsFunders);
            logger.info("ClientsFunders created successfully.");

        }catch (Exception e) {
            logger.error("Failed to create ClientsFunders: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ClientsFundersVO> GetAllFundersByClient(String clientId){
        logger.info("Getting all funders by clientId: {}");
        try{
            List<Map<String,Object>> fundersList = clientsFundersRepository.findFundersByClientId(Long.valueOf(clientId));
            if (!fundersList.isEmpty()) {
                logger.info("Found fundersList.");
                List<ClientsFundersVO> clientsFundersVOList = new ArrayList<>();
                for(Map<String,Object> funder: fundersList){
                    ClientsFundersVO clientsFundersVO = new ClientsFundersVO();
                    clientsFundersVO.setFunderId(String.valueOf(funder.get("client_funder_id")));
                    clientsFundersVO.setClientId(String.valueOf(funder.get("client_id")));
                    clientsFundersVO.setPayerName(String.valueOf(funder.get("payer_name")));
                    clientsFundersVO.setPlanName(String.valueOf(funder.get("plan_name")));
                    clientsFundersVO.setMemberId(String.valueOf(funder.get("member_id")));
                    clientsFundersVO.setGroupNumber(String.valueOf(funder.get("group_number")));
                    clientsFundersVO.setRelationshipToClient(String.valueOf(funder.get("relationship_to_client")));
                    clientsFundersVO.setPolicyHolderName(String.valueOf(funder.get("policy_holder_name")));
                    clientsFundersVO.setPolicyHolderPhone(String.valueOf(funder.get("policy_holder_phone")));
                    clientsFundersVO.setPolicyHolderEmail(String.valueOf(funder.get("policy_holder_email")));
                    clientsFundersVO.setPolicyHolderAddress(String.valueOf(funder.get("policy_holder_address")));
                    clientsFundersVO.setPolicyHolderCity(String.valueOf(funder.get("policy_holder_city")));
                    clientsFundersVO.setPolicyHolderState(String.valueOf(funder.get("policy_holder_state")));
                    clientsFundersVO.setPolicyHolderZipCode(String.valueOf(funder.get("policy_holder_zip_code")));
                    clientsFundersVO.setCoverageOrder(String.valueOf(funder.get("coverage_order")));
                    clientsFundersVO.setEffectiveStart(String.valueOf(funder.get("effective_start")));
                    clientsFundersVO.setEffectiveEnd(String.valueOf(funder.get("effective_end")));
                    clientsFundersVO.setIsActive(String.valueOf(funder.get("is_active")));
                    clientsFundersVO.setNotes(String.valueOf(funder.get("notes")));
                    clientsFundersVO.setCreatedAt(String.valueOf(funder.get("created_at")));
                    clientsFundersVO.setModifiedAt(String.valueOf(funder.get("modified_at")));
                    clientsFundersVOList.add(clientsFundersVO);
                }
                return clientsFundersVOList;
            }else{
                logger.info("No fundersList found.");
                return Collections.emptyList();
            }
        }catch (Exception e) {
            logger.error("Failed to get fundersList: {}", e.getMessage(), e);
        }
        return Collections.emptyList();
    }
    public ClientsFundersVO GetClientsFunders(String funderId){
        logger.info("Getting ClientsFunders: {}", funderId);
        try{
            ClientsFunders clientsFunders = clientsFundersRepository.findById(Long.valueOf(funderId)).orElse(null);
            if(clientsFunders != null){
                logger.info("Found ClientsFunders.");
                return ConvertToClientsFundersVO(clientsFunders);
            }else{
                logger.info("No ClientsFunders found.");
                return null;
            }
        }catch (Exception e) {
            logger.error("Failed to find ClientsFunders: {}", e.getMessage(), e);
        }
        return null;
    }

    public void UpdateClientsFunders(ClientsFundersDTO clientsFundersDTO){
        logger.info("Updating ClientsFunders: {}", "Funder ID:"+clientsFundersDTO.getFunderId(),"Client ID:"+clientsFundersDTO.getClientId());
        try{
            clientsFundersRepository.UpdateClientsFunderByClientIdAndFunderId(Long.valueOf(clientsFundersDTO.getClientId()),
                    Long.valueOf(clientsFundersDTO.getFunderId()),
                    clientsFundersDTO.getPayerName(),
                    clientsFundersDTO.getPlanName(),
                    clientsFundersDTO.getMemberId(),
                    clientsFundersDTO.getGroupNumber(),
                    clientsFundersDTO.getRelationshipToClient(),
                    clientsFundersDTO.getPolicyHolderName(),
                    clientsFundersDTO.getPolicyHolderPhone(),
                    clientsFundersDTO.getPolicyHolderEmail(),
                    clientsFundersDTO.getPolicyHolderAddress(),
                    clientsFundersDTO.getPolicyHolderCity(),
                    clientsFundersDTO.getPolicyHolderState(),
                    clientsFundersDTO.getPolicyHolderZipCode(),
                    clientsFundersDTO.getCoverageOrder(),
                    clientsFundersDTO.getEffectiveStart(),
                    clientsFundersDTO.getEffectiveEnd(),
                    clientsFundersDTO.getIsActive(),
                    clientsFundersDTO.getNotes()
                    );
            logger.info("ClientsFunders updated successfully.");
        }catch (Exception e) {
            logger.error("Failed to update ClientsFunders: {}", e.getMessage(), e);
        }
    }
    public ClientsFundersVO ConvertToClientsFundersVO(ClientsFunders clientsFunders){
        logger.info("Converting to ClientsFundersVO: {}", clientsFunders.getId());
        ClientsFundersVO clientsFundersVO = new ClientsFundersVO();
        clientsFundersVO.setFunderId(String.valueOf(clientsFunders.getId()));
        clientsFundersVO.setClientId(String.valueOf(clientsFunders.getClientId()));
        clientsFundersVO.setPayerName(clientsFunders.getPayerName());
        clientsFundersVO.setPlanName(clientsFunders.getPlanName());
        clientsFundersVO.setMemberId(clientsFunders.getMemberId());
        clientsFundersVO.setGroupNumber(clientsFunders.getGroupNumber());
        clientsFundersVO.setRelationshipToClient(clientsFunders.getRelationshipToClient());
        clientsFundersVO.setPolicyHolderName(clientsFunders.getPolicyHolderName());
        clientsFundersVO.setPolicyHolderPhone(clientsFunders.getPolicyHolderPhone());
        clientsFundersVO.setPolicyHolderEmail(clientsFunders.getPolicyHolderEmail());
        clientsFundersVO.setPolicyHolderAddress(clientsFunders.getPolicyHolderAddress());
        clientsFundersVO.setPolicyHolderCity(clientsFunders.getPolicyHolderCity());
        clientsFundersVO.setPolicyHolderState(clientsFunders.getPolicyHolderState());
        clientsFundersVO.setPolicyHolderZipCode(clientsFunders.getPolicyHolderZipCode());
        clientsFundersVO.setCoverageOrder(clientsFunders.getCoverageOrder());
        clientsFundersVO.setEffectiveStart(clientsFunders.getEffectiveStart());
        clientsFundersVO.setEffectiveEnd(clientsFunders.getEffectiveEnd());
        clientsFundersVO.setIsActive(clientsFunders.getIsActive());
        clientsFundersVO.setNotes(clientsFunders.getNotes());
        String formattedCreatedDateTime = DateTimeConverter.DateTimeConvertFromInstant(clientsFunders.getCreatedAt());
        String formattedModifiedDateTime = DateTimeConverter.DateTimeConvertFromInstant(clientsFunders.getModifiedAt());
        clientsFundersVO.setCreatedAt(formattedCreatedDateTime);
        clientsFundersVO.setModifiedAt(formattedModifiedDateTime);
        logger.info("ClientsFundersVO converted successfully.");
        return clientsFundersVO;
    }
}
