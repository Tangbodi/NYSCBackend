package com.example.demo.Service.ClientsReferringProviders;

import com.example.demo.Model.DTO.ClientsReferringProvidersDTO;
import com.example.demo.Model.Entity.ClientsReferringProviders;
import com.example.demo.Model.VO.ClientsReferringProvidersVO;
import com.example.demo.Repository.ClientsReferringProvidersRepository;
import com.example.demo.Service.ClientsInfo.ClientsInfoService;
import com.example.demo.Util.DateTimeConverter;
import com.example.demo.Util.Snowflake;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void CreateClientsReferringProviders(ClientsReferringProvidersDTO clientsReferringProvidersDTO){
        logger.info("Creating ClientsReferringProviders: {}");
        try{
            ClientsReferringProviders clientsReferringProviders = new ClientsReferringProviders();
            Long snowflakeId = Snowflake.generateUniqueId();
            clientsReferringProviders.setId(snowflakeId);
            clientsReferringProviders.setClientId(Long.valueOf(clientsReferringProvidersDTO.getClientId()));
            clientsReferringProviders.setProviderLastName(clientsReferringProvidersDTO.getLastName());
            clientsReferringProviders.setProviderFirstName(clientsReferringProvidersDTO.getFirstName());
            clientsReferringProviders.setProviderMiddleName(clientsReferringProvidersDTO.getMiddleName());
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
            logger.error("Failed to register ClientsReferringProviders: {}", e.getMessage(), e);
            throw e;
        }
    }
    public ClientsReferringProvidersVO GetClientsReferringProviders(String providerId){
        logger.info("Getting ClientsReferringProviders: {}", providerId);
        try{
            ClientsReferringProviders clientsReferringProviders = clientsReferringProvidersRepository.findById(Long.valueOf(providerId)).orElse(null);
            if(clientsReferringProviders!=null){
                logger.info("Found ClientsReferringProviders.");
                return ConvertToClientsReferringProvidersVO(clientsReferringProviders);
            }else{
                logger.info("No ClientsReferringProviders found.");
                return null;
            }
        }catch (Exception e) {
            logger.error("Failed to find ClientsReferringProviders: {}", e.getMessage(), e);
        }
        return null;
    }
    @Transactional
    public void UpdateClientsReferringProviders(ClientsReferringProvidersDTO clientsReferringProvidersDTO){
        logger.info("Updating ClientsReferringProviders: {}", "Provider ID:"+clientsReferringProvidersDTO.getProviderId(),"Client ID:"+clientsReferringProvidersDTO.getClientId());
        try{
            clientsReferringProvidersRepository.UpdateClientsReferringProviderByClientIdAndProviderId(
                    Long.valueOf(clientsReferringProvidersDTO.getProviderId()),
                    Long.valueOf(clientsReferringProvidersDTO.getClientId()),
                    clientsReferringProvidersDTO.getFirstName(),
                    clientsReferringProvidersDTO.getLastName(),
                    clientsReferringProvidersDTO.getMiddleName(),
                    clientsReferringProvidersDTO.getNpiNumber(),
                    clientsReferringProvidersDTO.getIsActive(),
                    clientsReferringProvidersDTO.getTaxonomyCode(),
                    clientsReferringProvidersDTO.getPhone(),
                    clientsReferringProvidersDTO.getFax(),
                    clientsReferringProvidersDTO.getAddress(),
                    clientsReferringProvidersDTO.getCity(),
                    clientsReferringProvidersDTO.getState(),
                    clientsReferringProvidersDTO.getZipCode(),
                    clientsReferringProvidersDTO.getNotes()
            );
            logger.info("ClientsReferringProviders updated successfully.");
        }catch (Exception e) {
            logger.error("Failed to update ClientsReferringProviders: {}", e.getMessage(), e);
        }
    }
    public ClientsReferringProvidersVO ConvertToClientsReferringProvidersVO(ClientsReferringProviders clientsReferringProviders){
        logger.info("Converting to ClientsReferringProvidersVO: {}", clientsReferringProviders.getId());
        ClientsReferringProvidersVO clientsReferringProvidersVO = new ClientsReferringProvidersVO();
        clientsReferringProvidersVO.setProviderId(String.valueOf(clientsReferringProviders.getId()));
        clientsReferringProvidersVO.setClientId(String.valueOf(clientsReferringProviders.getClientId()));
        clientsReferringProvidersVO.setFirstName(clientsReferringProviders.getProviderFirstName());
        clientsReferringProvidersVO.setLastName(clientsReferringProviders.getProviderLastName());
        clientsReferringProvidersVO.setMiddleName(clientsReferringProviders.getProviderMiddleName());
        clientsReferringProvidersVO.setNpiNumber(clientsReferringProviders.getNpiNumber());
        clientsReferringProvidersVO.setIsActive(clientsReferringProviders.getIsActive());
        clientsReferringProvidersVO.setTaxonomyCode(clientsReferringProviders.getTaxonomyCode());
        clientsReferringProvidersVO.setPhone(clientsReferringProviders.getPhone());
        clientsReferringProvidersVO.setFax(clientsReferringProviders.getFax());
        clientsReferringProvidersVO.setAddress(clientsReferringProviders.getAddress());
        clientsReferringProvidersVO.setCity(clientsReferringProviders.getCity());
        clientsReferringProvidersVO.setState(clientsReferringProviders.getState());
        clientsReferringProvidersVO.setZipCode(clientsReferringProviders.getZipCode());
        clientsReferringProvidersVO.setNotes(clientsReferringProviders.getNotes());
        String formattedCreatedDateTime = DateTimeConverter.DateTimeConvertFromInstant(clientsReferringProviders.getCreatedAt());
        String formattedModifiedDateTime = DateTimeConverter.DateTimeConvertFromInstant(clientsReferringProviders.getModifiedAt());
        clientsReferringProvidersVO.setCreatedAt(formattedCreatedDateTime);
        clientsReferringProvidersVO.setModifiedAt(formattedModifiedDateTime);
        logger.info("ClientsReferringProvidersVO converted successfully.");
        return clientsReferringProvidersVO;
    }
}
