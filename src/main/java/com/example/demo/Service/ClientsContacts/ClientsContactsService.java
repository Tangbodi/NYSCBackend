package com.example.demo.Service.ClientsContacts;


import com.example.demo.Model.DTO.ClientsContactsDTO;
import com.example.demo.Model.Entity.ClientsContacts;
import com.example.demo.Model.VO.ClientsContactsVO;
import com.example.demo.Repository.ClientsContactsRepository;
import com.example.demo.Util.DateTimeConverter;
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
    public void CreateClientsContacts(ClientsContactsDTO clientsContactsDTO) {
        logger.info("Creating ClientsContacts: {}", clientsContactsDTO.getClientId());
        try{
            ClientsContacts clientsContacts = new ClientsContacts();
            clientsContacts.setId(Long.valueOf(clientsContactsDTO.getClientId()));
            clientsContacts.setFirstName(clientsContactsDTO.getFirstName());
            clientsContacts.setLastName(clientsContactsDTO.getLastName());
            clientsContacts.setMiddleName(emptyIfNull(clientsContactsDTO.getMiddleName()));
            clientsContacts.setRelationshipType(clientsContactsDTO.getRelationshipType());
            clientsContacts.setIsPrimary(emptyIfNull(clientsContactsDTO.getIsPrimary()));
            clientsContacts.setPhone(clientsContactsDTO.getPhone());
            clientsContacts.setEmail(clientsContactsDTO.getEmail());
            clientsContacts.setAddress(clientsContactsDTO.getAddress());
            clientsContacts.setCity(clientsContactsDTO.getCity());
            clientsContacts.setState(clientsContactsDTO.getState());
            clientsContacts.setZipCode(clientsContactsDTO.getZipCode());
            clientsContacts.setNotes(emptyIfNull(clientsContactsDTO.getNotes()));
            clientsContacts.setCreatedAt(Instant.now());
            clientsContacts.setModifiedAt(Instant.now());
            clientsContactsRepository.save(clientsContacts);
            logger.info("ClientsContacts created successfully.");

        } catch (Exception e) {
            logger.error("Failed to create ClientsContacts: {}", e.getMessage(), e);
            throw e;
        }
    }
    public ClientsContactsVO GetClientsContacts(String clientId){
        logger.info("Getting ClientsContacts: {}", clientId);
        try{
            ClientsContacts clientsContacts = clientsContactsRepository.findById(Long.valueOf(clientId)).orElse(null);
            if(clientsContacts != null){
                logger.info("Found ClientsContacts: {}");
                return ConvertToClientsContactsVO(clientsContacts);
            }else{
                logger.info("ClientsContacts does not exist.");
                return null;
            }
        }catch (Exception e) {
            logger.error("Failed to get ClientsContacts: {}", e.getMessage(), e);
        }
        return null;
    }
    public void UpdateClientsContacts(ClientsContactsDTO clientsContactsDTO){
        logger.info("Updating ClientsContacts: {}", clientsContactsDTO.getClientId());
        try{
            clientsContactsRepository.UpdateClientsContacts(
                    Long.valueOf(clientsContactsDTO.getClientId()),
                    clientsContactsDTO.getFirstName(),
                    clientsContactsDTO.getLastName(),
                    clientsContactsDTO.getMiddleName(),
                    clientsContactsDTO.getRelationshipType(),
                    clientsContactsDTO.getIsPrimary(),
                    clientsContactsDTO.getPhone(),
                    clientsContactsDTO.getEmail(),
                    clientsContactsDTO.getAddress(),
                    clientsContactsDTO.getCity(),
                    clientsContactsDTO.getState(),
                    clientsContactsDTO.getZipCode(),
                    clientsContactsDTO.getNotes()
            );
            logger.info("ClientsContacts updated successfully.");
        }catch (Exception e) {
            logger.error("Failed to update ClientsContacts: {}", e.getMessage(), e);
        }
    }

    public ClientsContactsVO ConvertToClientsContactsVO(ClientsContacts clientsContacts){
        logger.info("Converting to ClientsContactsVO: {}", clientsContacts.getId());
        ClientsContactsVO clientsContactsVO = new ClientsContactsVO();
        clientsContactsVO.setClientId(String.valueOf(clientsContacts.getId()));
        clientsContactsVO.setFirstName(clientsContacts.getFirstName());
        clientsContactsVO.setLastName(clientsContacts.getFirstName());
        clientsContactsVO.setMiddleName(clientsContacts.getMiddleName());
        clientsContactsVO.setRelationshipType(clientsContacts.getRelationshipType());
        clientsContactsVO.setIsPrimary(clientsContacts.getIsPrimary());
        clientsContactsVO.setPhone(clientsContacts.getPhone());
        clientsContactsVO.setEmail(clientsContacts.getEmail());
        clientsContactsVO.setAddress(clientsContacts.getAddress());
        clientsContactsVO.setCity(clientsContacts.getCity());
        clientsContactsVO.setState(clientsContacts.getState());
        clientsContactsVO.setZipCode(clientsContacts.getZipCode());
        clientsContactsVO.setNotes(clientsContacts.getNotes());
        String formattedCreatedDateTime = DateTimeConverter.DateTimeConvertFromInstant(clientsContacts.getCreatedAt());
        String formattedModifiedDateTime = DateTimeConverter.DateTimeConvertFromInstant(clientsContacts.getModifiedAt());
        clientsContactsVO.setCreatedAt(formattedCreatedDateTime);
        clientsContactsVO.setModifiedAt(formattedModifiedDateTime);
        return clientsContactsVO;
    }
    private String emptyIfNull(String s) {
        return s == null ? "" : s;
    }

}
