package com.example.demo.Service.ClientsFundersService;

import com.example.demo.Model.DTO.ClientsFundersDTO;
import com.example.demo.Model.Entity.ClientsFunders;
import com.example.demo.Model.VO.ClientsFundersVO;
import com.example.demo.Repository.ClientsFundersRepository;
import com.example.demo.Service.ClientsContacts.ClientsContactsService;
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
            clientsFunders.setClientId();
            clientsFunders.setPayerName();
            clientsFunders.setPlanName();
            clientsFunders.setMemberId();
            clientsFunders.setGroupNumber();
            clientsFunders.setRelationshipToClient();
            clientsFunders.setPolicyHolderName();
            clientsFunders.setPolicyHolderPhone();
            clientsFunders.setPolicyHolderEmail();
            clientsFunders.setPolicyHolderAddress();
            clientsFunders.setPolicyHolderCity();
            clientsFunders.setPolicyHolderState();
            clientsFunders.setPolicyHolderZipCode();
            clientsFunders.setCoverageOrder();
            clientsFunders.setEffectiveStart();
            clientsFunders.setEffectiveEnd();
            clientsFunders.setIsActive();
            clientsFunders.setNotes();
            clientsFunders.setCreatedAt(Instant.now());
            clientsFunders.setModifiedAt(Instant.now());
            clientsFundersRepository.save(clientsFunders);
            logger.info("ClientsFunders created successfully.");

        }catch (Exception e) {
            logger.error("Failed to create ClientsFunders: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<ClientsFundersVO> GetClientsFunders(Long clientId){
        logger.info("Getting ClientsFunders: {}");
        try{
            List<Map<String,Object>> fundersList = clientsFundersRepository.findByClientId(clientId);
            if (!fundersList.isEmpty()) {
                List<ClientsFundersVO> clientsFundersVOList = new ArrayList<>();
                for(Map<String,Object> funder: fundersList){
                    ClientsFundersVO clientsFundersVO = new ClientsFundersVO();
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
                logger.info("No ClientsFunders found.");
                return Collections.emptyList();
            }
        }catch (Exception e) {
            logger.error("Failed to update StaffsInfo: {}", e.getMessage(), e);
        }
        return Collections.emptyList();
    }
    public ClientsFundersVO ConvertToClientsFundersVO(ClientsFunders clientsFunders){
        logger.info("Converting to ClientsFundersVO: {}");
        ClientsFundersVO clientsFundersVO = new ClientsFundersVO();
        clientsFundersVO.setClientId();
        clientsFundersVO.setPayerName();
        clientsFundersVO.setPlanName();
        clientsFundersVO.setMemberId();
        clientsFundersVO.setGroupNumber();
        clientsFundersVO.setRelationshipToClient();
        clientsFundersVO.setPolicyHolderName();
        clientsFundersVO.setPolicyHolderPhone();
        clientsFundersVO.setPolicyHolderEmail();
        clientsFundersVO.setPolicyHolderAddress();
        clientsFundersVO.setPolicyHolderCity();
        clientsFundersVO.setPolicyHolderState();
        clientsFundersVO.setPolicyHolderZipCode();
        clientsFundersVO.setCoverageOrder();
        clientsFundersVO.setEffectiveStart();
        clientsFundersVO.setEffectiveEnd();
        clientsFundersVO.setIsActive();
        clientsFundersVO.setNotes();
        clientsFundersVO.setCreatedAt();
        clientsFundersVO.setModifiedAt();
        logger.info("ClientsFundersVO converted successfully.");
        return clientsFundersVO;
    }
}
