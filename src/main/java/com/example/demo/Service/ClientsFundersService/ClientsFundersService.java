package com.example.demo.Service.ClientsFundersService;

import com.example.demo.Model.Entity.ClientsFunders;
import com.example.demo.Repository.ClientsFundersRepository;
import com.example.demo.Service.ClientsContacts.ClientsContactsService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ClientsFundersService {
    private static final Logger logger = LoggerFactory.getLogger(ClientsFundersService.class);
    @Autowired
    private ClientsFundersRepository clientsFundersRepository;

    @Transactional
    public void CreateClientsFunders(){
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

    public clientsFundersVO GetClientsFunders(){

    }
}
