package com.example.demo.Service.ClientsContacts;


import com.example.demo.Model.DTO.ClientContactsDTO;
import com.example.demo.Model.Entity.ClientContacts;
import com.example.demo.Model.VO.ClientContactsVO;
import com.example.demo.Repository.ClientContactsRepository;
import com.example.demo.Util.DateTimeConverter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ClientContactsService {
    private static final Logger logger = LoggerFactory.getLogger(ClientContactsService.class);

    @Autowired
    private ClientContactsRepository clientContactsRepository;

    @Transactional
    public void CreateClientsContacts(ClientContactsDTO clientContactsDTO) {
        logger.info("Creating ClientsContacts: {}", clientContactsDTO.getClientId());
        try{
            ClientContacts clientContacts = new ClientContacts();
            clientContacts.setId(Long.valueOf(clientContactsDTO.getClientId()));
            clientContacts.setFirstName(clientContactsDTO.getFirstName());
            clientContacts.setLastName(clientContactsDTO.getLastName());
            clientContacts.setRelationshipType(clientContactsDTO.getRelationshipType());
            clientContacts.setIsPrimary(emptyIfNull(clientContactsDTO.getIsPrimary()));
            clientContacts.setPhone(clientContactsDTO.getPhone());
            clientContacts.setEmail(clientContactsDTO.getEmail());
            clientContacts.setAddress(clientContactsDTO.getAddress());
            clientContacts.setCity(clientContactsDTO.getCity());
            clientContacts.setState(clientContactsDTO.getState());
            clientContacts.setZipCode(clientContactsDTO.getZipCode());
            clientContacts.setCreatedAt(Instant.now());
            clientContacts.setModifiedAt(Instant.now());
            clientContactsRepository.save(clientContacts);
            logger.info("ClientsContacts created successfully.");

        } catch (Exception e) {
            logger.error("Failed to create ClientsContacts: {}", e.getMessage(), e);
            throw e;
        }
    }
    public ClientContactsVO GetClientsContacts(String clientId){
        logger.info("Getting ClientsContacts: {}", clientId);
        try{
            ClientContacts clientContacts = clientContactsRepository.findById(Long.valueOf(clientId)).orElse(null);
            if(clientContacts != null){
                logger.info("Found ClientsContacts: {}");
                return ConvertToClientsContactsVO(clientContacts);
            }else{
                logger.info("ClientsContacts does not exist.");
                return null;
            }
        }catch (Exception e) {
            logger.error("Failed to get ClientsContacts: {}", e.getMessage(), e);
        }
        return null;
    }
    @Transactional
    public void UpdateClientsContacts(ClientContactsDTO clientContactsDTO){
        logger.info("Updating ClientsContacts: {}", clientContactsDTO.getClientId());
        try{
            clientContactsRepository.UpdateClientsContacts(
                    Long.valueOf(clientContactsDTO.getClientId()),
                    clientContactsDTO.getFirstName(),
                    clientContactsDTO.getLastName(),
                    clientContactsDTO.getRelationshipType(),
                    clientContactsDTO.getIsPrimary(),
                    clientContactsDTO.getPhone(),
                    clientContactsDTO.getEmail(),
                    clientContactsDTO.getAddress(),
                    clientContactsDTO.getCity(),
                    clientContactsDTO.getState(),
                    clientContactsDTO.getZipCode()
            );
            logger.info("ClientsContacts updated successfully.");
        }catch (Exception e) {
            logger.error("Failed to update ClientsContacts: {}", e.getMessage(), e);
        }
    }

    public ClientContactsVO ConvertToClientsContactsVO(ClientContacts clientContacts){
        logger.info("Converting to ClientsContactsVO: {}", clientContacts.getId());
        ClientContactsVO clientContactsVO = new ClientContactsVO();
        clientContactsVO.setClientId(String.valueOf(clientContacts.getId()));
        clientContactsVO.setFirstName(clientContacts.getFirstName());
        clientContactsVO.setLastName(clientContacts.getLastName());
        clientContactsVO.setRelationshipType(clientContacts.getRelationshipType());
        clientContactsVO.setIsPrimary(clientContacts.getIsPrimary());
        clientContactsVO.setPhone(clientContacts.getPhone());
        clientContactsVO.setEmail(clientContacts.getEmail());
        clientContactsVO.setAddress(clientContacts.getAddress());
        clientContactsVO.setCity(clientContacts.getCity());
        clientContactsVO.setState(clientContacts.getState());
        clientContactsVO.setZipCode(clientContacts.getZipCode());
        String formattedCreatedDateTime = DateTimeConverter.DateTimeConvertFromInstant(clientContacts.getCreatedAt());
        String formattedModifiedDateTime = DateTimeConverter.DateTimeConvertFromInstant(clientContacts.getModifiedAt());
        clientContactsVO.setCreatedAt(formattedCreatedDateTime);
        clientContactsVO.setModifiedAt(formattedModifiedDateTime);
        return clientContactsVO;
    }
    @Transactional
    public void DeleteClientsContacts(String clientId) {
        logger.info("Deleting ClientsContacts: {}", clientId);
        try {
            Long id = Long.valueOf(clientId);
            if (!clientContactsRepository.existsById(id)) {
                throw new RuntimeException("Client contacts not found for id: " + clientId);
            }
            clientContactsRepository.deleteById(id);
            logger.info("ClientsContacts deleted successfully.");
        } catch (Exception e) {
            logger.error("Failed to delete ClientsContacts: {}", e.getMessage(), e);
            throw e;
        }
    }

    private String emptyIfNull(String s) {
        return s == null ? "" : s;
    }

}
