package com.example.demo.Service.ClientsReferringProviders;

import com.example.demo.Model.DTO.ClientsReferringProvidersDTO;
import com.example.demo.Model.Entity.ClientsReferringProviders;
import com.example.demo.Repository.ClientsReferringProvidersRepository;
import com.example.demo.Service.ClientsInfo.ClientsInfoService;
import com.example.demo.Util.Snowflake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ClientsReferringProvidersService {
    private static final Logger logger = LoggerFactory.getLogger(ClientsReferringProvidersService.class);
    @Autowired
    private ClientsReferringProvidersRepository clientsReferringProvidersRepository;

    public void CreateClientsReferringProviders(ClientsReferringProvidersDTO clientsReferringProvidersDTO){
        logger.info("Creating ClientsReferringProviders: {}");
        try{
            ClientsReferringProviders clientsReferringProviders = new ClientsReferringProviders();
            Long snowflakeId = Snowflake.generateUniqueId();
            clientsReferringProviders.setId(snowflakeId);
            clientsReferringProviders.setClientId(Long.valueOf(clientsReferringProvidersDTO.getClientId()));
            clientsReferringProviders.setProviderLastName(clientsReferringProvidersDTO.getProviderLastName());
            clientsReferringProviders.setProviderFirstName(clientsReferringProvidersDTO.getProviderFirstName());
            clientsReferringProviders.setProviderMiddleName(clientsReferringProvidersDTO.getProviderMiddleName());
            clientsReferringProviders.setNpiNumber(clientsReferringProvidersDTO.getNpiNumber());
            clientsReferringProviders.setIsActive(clientsReferringProvidersDTO.getIsActive());
            clientsReferringProviders.setTaxonomyCode(clientsReferringProvidersDTO.getTaxonomyCode());
            clientsReferringProviders.setAddress(clientsReferringProvidersDTO.getAddress());
            clientsReferringProviders.setCity(clientsReferringProvidersDTO.getCity());
            clientsReferringProviders.setState(clientsReferringProvidersDTO.getState());
            clientsReferringProviders.setZipCode(clientsReferringProvidersDTO.getZipCode());
            clientsReferringProviders.setPhone(clientsReferringProvidersDTO.getPhone());
            clientsReferringProviders.setFax(clientsReferringProvidersDTO.getFax());
            clientsReferringProviders.setNotes(clientsReferringProvidersDTO.getNotes());
            clientsReferringProviders.setCreatedAt(Instant.now());
            clientsReferringProviders.setModifiedAt(Instant.now());
            clientsReferringProvidersRepository.save(clientsReferringProviders);
            logger.info("ClientsReferringProviders created successfully.");
        } catch (Exception e) {
            logger.error("Failed to register ClientsInfo: {}", e.getMessage(), e);
            throw e;
        }
    }
}
