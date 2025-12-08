package com.example.demo.Service.ClientsInfo;

import com.example.demo.Controller.StaffsLoginController;
import com.example.demo.Model.DTO.ClientsInfoDTO;
import com.example.demo.Model.Entity.ClientsInfo;
import com.example.demo.Repository.ClientsInfoRepository;
import com.example.demo.Util.Snowflake;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ClientsInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ClientsInfoService.class);

    @Autowired
    private ClientsInfoRepository clientsInfoRepository;
    @Transactional
    public void RegisterClientsInfo(ClientsInfoDTO clientsInfoDTO) {
        logger.info("Registering ClientsInfo: {}", clientsInfoDTO.getClientFirstName() + "." + clientsInfoDTO.getClientLastName());

        try {
            logger.info("Creating UUID for ClientsInfo: {}", clientsInfoDTO.getClientFirstName());
            Long snowflakeId = Snowflake.generateUniqueId();

            logger.info("Creating ClientsInfo:{}", clientsInfoDTO.getClientFirstName());
            ClientsInfo clientsInfo = new ClientsInfo();
            clientsInfo.setId(snowflakeId);
            clientsInfo.setClientLastName(clientsInfoDTO.getClientLastName());
            clientsInfo.setClientFirstName(clientsInfoDTO.getClientFirstName());
            clientsInfo.setClientMiddleName(clientsInfoDTO.getClientMiddleName());
            clientsInfo.setDateOfBirth(clientsInfoDTO.getDateOfBirth());
            clientsInfo.setGender(clientsInfoDTO.getGender());
            clientsInfo.setStatus(clientsInfoDTO.getStatus());
            clientsInfo.setAddress(clientsInfoDTO.getAddress());
            clientsInfo.setCity(clientsInfoDTO.getCity());
            clientsInfo.setState(clientsInfoDTO.getState());
            clientsInfo.setZipCode(clientsInfoDTO.getZipCode());
            clientsInfo.setNotes(clientsInfoDTO.getNotes());
            clientsInfo.setCreatedAt(Instant.now());
            clientsInfo.setModifiedAt(Instant.now());

            if (clientsInfoRepository.save(clientsInfo) == null) {
                throw new StaffsLoginController.UserRegistrationException("Failed to register ClientsInfo.");
            }
            logger.info("ClientsInfo registered successfully.");

        } catch (Exception e) {
            logger.error("Failed to register ClientsInfo: {}", e.getMessage(), e);
            throw e;  // <--- DO NOT wrap, return exact error
        }
    }
    @Transactional
    public void UpdateClientsInfo(ClientsInfoDTO clientsInfoDTO) {
        logger.info("Updating ClientsInfo: {}", clientsInfoDTO.getClientFirstName() + "." + clientsInfoDTO.getClientLastName());

        clientsInfoRepository.UpdateClientsInfo(
                clientsInfoDTO.getClientId(),
                clientsInfoDTO.getClientFirstName(),
                clientsInfoDTO.getClientLastName(),
                clientsInfoDTO.getClientMiddleName(),
                clientsInfoDTO.getDateOfBirth(),
                clientsInfoDTO.getGender(),
                clientsInfoDTO.getStatus(),
                clientsInfoDTO.getAddress(),
                clientsInfoDTO.getCity(),
                clientsInfoDTO.getState(),
                clientsInfoDTO.getZipCode(),
                clientsInfoDTO.getNotes()
        );
    }
}
