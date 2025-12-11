package com.example.demo.Service.ClientsContacts;

import com.example.demo.Controller.StaffsLoginController;
import com.example.demo.Model.DTO.ClientsInfoDTO;
import com.example.demo.Model.Entity.ClientsContacts;
import com.example.demo.Repository.ClientsContactsRepository;
import com.example.demo.Service.ClientsInfo.ClientsInfoService;
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
    public void CreateClientsContacts(ClientsInfoDTO clientsInfoDTO) {
        logger.info("Creating ClientsContacts: {}", clientsInfoDTO.getClientId());
        try{
            ClientsContacts clientsContacts = new ClientsContacts();
            clientsContacts.setId(clientsInfoDTO.getClientId());
            clientsContacts.setFirstName(clientsInfoDTO.getFirstName());
            clientsContacts.setLastName(clientsInfoDTO.getLastName());
            clientsContacts.setMiddleName(clientsInfoDTO.getMiddleName());
            clientsContacts.setRelationshipType(clientsInfoDTO.getRelationshipType());
            clientsContacts.setIsPrimary(clientsInfoDTO.getIsPrimary());
            clientsContacts.setPhone(clientsInfoDTO.getPhone());
            clientsContacts.setEmail(clientsInfoDTO.getEmail());
            clientsContacts.setAddress(clientsInfoDTO.getAddress());
            clientsContacts.setCity(clientsInfoDTO.getCity());
            clientsContacts.setState(clientsInfoDTO.getState());
            clientsContacts.setZipCode(clientsInfoDTO.getZipCode());
            clientsContacts.setNotes(clientsInfoDTO.getNotes());
            clientsContacts.setCreatedAt(Instant.now());
            clientsContacts.setModifiedAt(Instant.now());
            clientsContactsRepository.save(clientsContacts);
            logger.info("ClientsContacts created successfully.");

        } catch (Exception e) {
            logger.error("Failed to create ClientsContacts: {}", e.getMessage(), e);
            throw e;
        }
    }
}
