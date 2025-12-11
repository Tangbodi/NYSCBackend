package com.example.demo.Service.ClientsReferringProviders;

import com.example.demo.Model.Entity.ClientsReferringProviders;
import com.example.demo.Repository.ClientsReferringProvidersRepository;
import com.example.demo.Service.ClientsInfo.ClientsInfoService;
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

    public void CreateClientsReferringProviders(){
        logger.info("Creating ClientsReferringProviders: {}");
        try{
            ClientsReferringProviders clientsReferringProviders = new ClientsReferringProviders();
            clientsReferringProviders.setClientId();
            clientsReferringProviders.setProviderLastName();
            clientsReferringProviders.setProviderFirstName();
            clientsReferringProviders.setProviderMiddleName();
            clientsReferringProviders.setNpiNumber();
            clientsReferringProviders.setIsActive();
            clientsReferringProviders.setTaxonomyCode();
            clientsReferringProviders.setAddress();
            clientsReferringProviders.setCity();
            clientsReferringProviders.setState();
            clientsReferringProviders.setZipCode();
            clientsReferringProviders.setPhone();
            clientsReferringProviders.setFax();
            clientsReferringProviders.setNotes();
            clientsReferringProviders.setCreatedAt(Instant.now());
            clientsReferringProviders.setModifiedAt(Instant.now());
            clientsReferringProvidersRepository.save(clientsReferringProviders);
            logger.info("ClientsReferringProviders created successfully.");
        } catch (Exception e) {
            logger.error("Failed to register ClientsInfo: {}", e.getMessage(), e);
            throw e;  // <--- DO NOT wrap, return exact error
        }
    }
}
