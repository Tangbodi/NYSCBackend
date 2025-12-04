package com.example.demo.Service.ClientsInfo;

import com.example.demo.Controller.StaffsController;
import com.example.demo.Model.DTO.ClientsRegisterDTO;
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
    public void SaveClientsInfo(ClientsRegisterDTO clientsRegisterDTO) {
        logger.info("Registering client: {}", clientsRegisterDTO.getClientFirstName() + "." + clientsRegisterDTO.getClientFirstName());

        try {
            logger.info("Creating UUID for client: {}", clientsRegisterDTO.getClientFirstName());
            Long snowflakeId = Snowflake.generateUniqueId();

            logger.info("Creating ClientsInfo:{}", clientsRegisterDTO.getClientFirstName());
            ClientsInfo clientsInfo = new ClientsInfo();
            clientsInfo.setId(snowflakeId);
            clientsInfo.setClientLastName(clientsRegisterDTO.getClientLastName());
            clientsInfo.setClientFirstName(clientsRegisterDTO.getClientFirstName());
            clientsInfo.setClientMiddleName(clientsRegisterDTO.getClientMiddleName());
            clientsInfo.setDateOfBirth(clientsRegisterDTO.getDateOfBirth());
            clientsInfo.setGender(clientsRegisterDTO.getGender());
            clientsInfo.setStatus(clientsRegisterDTO.getStatus());
            clientsInfo.setAddress(clientsRegisterDTO.getAddress());
            clientsInfo.setCity(clientsRegisterDTO.getCity());
            clientsInfo.setState(clientsRegisterDTO.getState());
            clientsInfo.setZipCode(clientsRegisterDTO.getZipCode());
            clientsInfo.setNotes(clientsRegisterDTO.getNotes());
            clientsInfo.setCreatedAt(Instant.now());
            clientsInfo.setModifiedAt(Instant.now());

            if (clientsInfoRepository.save(clientsInfo) == null) {
                throw new StaffsController.UserRegistrationException("Failed to save ClientsInfo");
            }
            logger.info("ClientsInfo saved successfully");

        } catch (Exception e) {
            logger.error("Failed to register staff: {}", e.getMessage(), e);
            throw e;  // <--- DO NOT wrap, return exact error
        }
    }
}
