package com.example.demo.Service.ClientsReferringProviders;

import com.example.demo.Model.DTO.ClientReferringProvidersDTO;
import com.example.demo.Model.Entity.ClientReferringProviders;
import com.example.demo.Model.VO.ClientReferringProvidersVO;
import com.example.demo.Repository.ClientReferringProvidersRepository;
import com.example.demo.Util.DateTimeConverter;
import com.example.demo.Util.Snowflake;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ClientReferringProvidersService {
    private static final Logger logger = LoggerFactory.getLogger(ClientReferringProvidersService.class);
    @Autowired
    private ClientReferringProvidersRepository clientReferringProvidersRepository;

    @Transactional
    public void CreateClientsReferringProviders(ClientReferringProvidersDTO clientReferringProvidersDTO){
        logger.info("Creating ClientsReferringProviders: {}");
        try{
            ClientReferringProviders clientReferringProviders = new ClientReferringProviders();
            Long snowflakeId = Snowflake.generateUniqueId();
            clientReferringProviders.setId(snowflakeId);
            clientReferringProviders.setClientId(Long.valueOf(clientReferringProvidersDTO.getClientId()));
            clientReferringProviders.setProviderLastName(clientReferringProvidersDTO.getLastName());
            clientReferringProviders.setProviderFirstName(clientReferringProvidersDTO.getFirstName());
            clientReferringProviders.setNpiNumber(clientReferringProvidersDTO.getNpiNumber());
            clientReferringProviders.setIsActive(clientReferringProvidersDTO.getIsActive());
            clientReferringProviders.setTaxonomyCode(clientReferringProvidersDTO.getTaxonomyCode());
            clientReferringProviders.setAddress(clientReferringProvidersDTO.getAddress());
            clientReferringProviders.setCity(clientReferringProvidersDTO.getCity());
            clientReferringProviders.setState(clientReferringProvidersDTO.getState());
            clientReferringProviders.setZipCode(clientReferringProvidersDTO.getZipCode());
            clientReferringProviders.setPhone(clientReferringProvidersDTO.getPhone());
            clientReferringProviders.setFax(clientReferringProvidersDTO.getFax());
            clientReferringProviders.setCreatedAt(Instant.now());
            clientReferringProviders.setModifiedAt(Instant.now());
            clientReferringProvidersRepository.save(clientReferringProviders);
            logger.info("ClientsReferringProviders created successfully.");
        } catch (Exception e) {
            logger.error("Failed to register ClientsReferringProviders: {}", e.getMessage(), e);
            throw e;
        }
    }
    public ClientReferringProvidersVO GetClientsReferringProviders(String providerId){
        logger.info("Getting ClientsReferringProviders: {}", providerId);
        try{
            ClientReferringProviders clientReferringProviders = clientReferringProvidersRepository.findById(Long.valueOf(providerId)).orElse(null);
            if(clientReferringProviders !=null){
                logger.info("Found ClientsReferringProviders.");
                return ConvertToClientsReferringProvidersVO(clientReferringProviders);
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
    public void UpdateClientsReferringProviders(ClientReferringProvidersDTO clientReferringProvidersDTO){
        logger.info("Updating ClientsReferringProviders: {}", "Provider ID:"+ clientReferringProvidersDTO.getProviderId(),"Client ID:"+ clientReferringProvidersDTO.getClientId());
        try{
            clientReferringProvidersRepository.UpdateClientsReferringProviderByClientIdAndProviderId(
                    Long.valueOf(clientReferringProvidersDTO.getProviderId()),
                    Long.valueOf(clientReferringProvidersDTO.getClientId()),
                    clientReferringProvidersDTO.getFirstName(),
                    clientReferringProvidersDTO.getLastName(),
                    clientReferringProvidersDTO.getNpiNumber(),
                    clientReferringProvidersDTO.getIsActive(),
                    clientReferringProvidersDTO.getTaxonomyCode(),
                    clientReferringProvidersDTO.getPhone(),
                    clientReferringProvidersDTO.getFax(),
                    clientReferringProvidersDTO.getAddress(),
                    clientReferringProvidersDTO.getCity(),
                    clientReferringProvidersDTO.getState(),
                    clientReferringProvidersDTO.getZipCode()
            );
            logger.info("ClientsReferringProviders updated successfully.");
        }catch (Exception e) {
            logger.error("Failed to update ClientsReferringProviders: {}", e.getMessage(), e);
        }
    }
    @Transactional
    public void DeleteClientsReferringProviders(String providerId) {
        logger.info("Deleting ClientsReferringProviders: {}", providerId);
        try {
            Long id = Long.valueOf(providerId);
            if (!clientReferringProvidersRepository.existsById(id)) {
                throw new RuntimeException("Referring provider not found for id: " + providerId);
            }
            clientReferringProvidersRepository.deleteById(id);
            logger.info("ClientsReferringProviders deleted successfully.");
        } catch (Exception e) {
            logger.error("Failed to delete ClientsReferringProviders: {}", e.getMessage(), e);
            throw e;
        }
    }

    public ClientReferringProvidersVO ConvertToClientsReferringProvidersVO(ClientReferringProviders clientReferringProviders){
        logger.info("Converting to ClientsReferringProvidersVO: {}", clientReferringProviders.getId());
        ClientReferringProvidersVO clientReferringProvidersVO = new ClientReferringProvidersVO();
        clientReferringProvidersVO.setProviderId(String.valueOf(clientReferringProviders.getId()));
        clientReferringProvidersVO.setClientId(String.valueOf(clientReferringProviders.getClientId()));
        clientReferringProvidersVO.setFirstName(clientReferringProviders.getProviderFirstName());
        clientReferringProvidersVO.setLastName(clientReferringProviders.getProviderLastName());
        clientReferringProvidersVO.setNpiNumber(clientReferringProviders.getNpiNumber());
        clientReferringProvidersVO.setIsActive(clientReferringProviders.getIsActive());
        clientReferringProvidersVO.setTaxonomyCode(clientReferringProviders.getTaxonomyCode());
        clientReferringProvidersVO.setPhone(clientReferringProviders.getPhone());
        clientReferringProvidersVO.setFax(clientReferringProviders.getFax());
        clientReferringProvidersVO.setAddress(clientReferringProviders.getAddress());
        clientReferringProvidersVO.setCity(clientReferringProviders.getCity());
        clientReferringProvidersVO.setState(clientReferringProviders.getState());
        clientReferringProvidersVO.setZipCode(clientReferringProviders.getZipCode());
        String formattedCreatedDateTime = DateTimeConverter.DateTimeConvertFromInstant(clientReferringProviders.getCreatedAt());
        String formattedModifiedDateTime = DateTimeConverter.DateTimeConvertFromInstant(clientReferringProviders.getModifiedAt());
        clientReferringProvidersVO.setCreatedAt(formattedCreatedDateTime);
        clientReferringProvidersVO.setModifiedAt(formattedModifiedDateTime);
        logger.info("ClientsReferringProvidersVO converted successfully.");
        return clientReferringProvidersVO;
    }
}
