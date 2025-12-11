package com.example.demo.Service.ClientsContacts;


import com.example.demo.Model.Entity.ClientsContacts;
import com.example.demo.Model.VO.ClientsContactsVO;
import com.example.demo.Repository.ClientsContactsRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ClientsContactsService {
    private static final Logger logger = LoggerFactory.getLogger(ClientsContactsService.class);

    @Autowired
    private ClientsContactsRepository clientsContactsRepository;

    @Transactional
    public void CreateClientsContacts() {
        logger.info("Creating ClientsContacts: {}");
        try{
            ClientsContacts clientsContacts = new ClientsContacts();
            clientsContacts.setId();
            clientsContacts.setFirstName();
            clientsContacts.setLastName();
            clientsContacts.setMiddleName();
            clientsContacts.setRelationshipType();
            clientsContacts.setIsPrimary();
            clientsContacts.setPhone();
            clientsContacts.setEmail();
            clientsContacts.setAddress();
            clientsContacts.setCity();
            clientsContacts.setState();
            clientsContacts.setZipCode();
            clientsContacts.setNotes();
            clientsContacts.setCreatedAt(Instant.now());
            clientsContacts.setModifiedAt(Instant.now());
            clientsContactsRepository.save(clientsContacts);
            logger.info("ClientsContacts created successfully.");

        } catch (Exception e) {
            logger.error("Failed to create ClientsContacts: {}", e.getMessage(), e);
            throw e;
        }
    }
    public ClientsContactsVO GetClientsContacts(){
        logger.info("Getting ClientsContacts: {}");
        try{
            ClientsContacts clientsContacts = clientsContactsRepository.findById().orElse(null);
            if(clientsContacts != null){
                logger.info("Found ClientsContacts: {}");
                return ConvertToClientsContactsVO();
            }else{
                logger.info("ClientsContacts does not exist.");
                return null;
            }
        }
    }
    public ClientsContactsVO ConvertToClientsContactsVO(){
        logger.info("Converting to ClientsContactsVO: {}");
        ClientsContactsVO clientsContactsVO = new ClientsContactsVO();
        clientsContactsVO.setClientId();
    }
}
