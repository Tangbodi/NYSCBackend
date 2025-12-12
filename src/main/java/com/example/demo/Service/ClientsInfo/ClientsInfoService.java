package com.example.demo.Service.ClientsInfo;

import com.example.demo.Controller.StaffsLoginController;
import com.example.demo.Model.DTO.ClientsInfoDTO;
import com.example.demo.Model.Entity.ClientsInfo;
import com.example.demo.Model.VO.ClientsInfoVO;
import com.example.demo.Repository.ClientsInfoRepository;
import com.example.demo.Service.ClientsContacts.ClientsContactsService;
import com.example.demo.Util.Snowflake;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static java.lang.Long.valueOf;

@Service
public class ClientsInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ClientsInfoService.class);

    @Autowired
    private ClientsInfoRepository clientsInfoRepository;
    @Autowired
    private ClientsContactsService clientsContactsService;
    @Transactional
    public void RegisterClientsInfo(ClientsInfoDTO clientsInfoDTO) {
        logger.info("Registering ClientsInfo: {}", clientsInfoDTO.getFirstName() + "." + clientsInfoDTO.getLastName());

        try {
            logger.info("Creating UUID for ClientsInfo: {}", clientsInfoDTO.getFirstName());
            Long snowflakeId = Snowflake.generateUniqueId();

            logger.info("Creating ClientsInfo:{}", clientsInfoDTO.getFirstName());
            ClientsInfo clientsInfo = new ClientsInfo();
            clientsInfo.setId(snowflakeId);
            clientsInfo.setLastName(clientsInfoDTO.getLastName());
            clientsInfo.setFirstName(clientsInfoDTO.getFirstName());
            clientsInfo.setMiddleName(clientsInfoDTO.getMiddleName());
            clientsInfo.setDateOfBirth(clientsInfoDTO.getDateOfBirth());
            clientsInfo.setGender(clientsInfoDTO.getGender());
            clientsInfo.setStatus(clientsInfoDTO.getStatus());
            clientsInfo.setAddress(emptyIfNull(clientsInfoDTO.getAddress()));
            clientsInfo.setCity(emptyIfNull(clientsInfoDTO.getCity()));
            clientsInfo.setState(emptyIfNull(clientsInfoDTO.getState()));
            clientsInfo.setZipCode(emptyIfNull(clientsInfoDTO.getZipCode()));
            clientsInfo.setNotes(emptyIfNull(clientsInfoDTO.getNotes()));
            clientsInfo.setCreatedAt(Instant.now());
            clientsInfo.setModifiedAt(Instant.now());
            clientsInfoRepository.save(clientsInfo);
            logger.info("ClientsInfo registered successfully.");
//            clientsContactsService.CreateClientsContacts(clientsInfoDTO);
        } catch (Exception e) {
            logger.error("Failed to register ClientsInfo: {}", e.getMessage(), e);
            throw e;  // <--- DO NOT wrap, return exact error
        }
    }
    @Transactional
    public ClientsInfoVO GetClientsInfo(String clientId){
        logger.info("Getting ClientsInfo: {}", clientId);
        try{
            ClientsInfo clientsInfo = clientsInfoRepository.findById(valueOf(clientId)).orElse(null);
            if (clientsInfo != null) {
                logger.info("Found ClientsInfo: {}" + clientsInfo.getFirstName() +"."+clientsInfo.getLastName());
                return ConvertToClientsInfoVO(clientsInfo);
            } else {
                logger.info("StaffsInfo does not exist.");
                return null;
            }
        }catch (Exception e) {
            logger.error("Failed to get StaffsInfo: {}", e.getMessage(), e);
        }
        return null;
    }
    @Transactional
    public void UpdateClientsInfo(ClientsInfoDTO clientsInfoDTO) {
        logger.info("Updating ClientsInfo: {}", clientsInfoDTO.getFirstName() + "." + clientsInfoDTO.getLastName());
        try{
            clientsInfoRepository.UpdateClientsInfo(
                    Long.valueOf(clientsInfoDTO.getClientId()),
                    clientsInfoDTO.getFirstName(),
                    clientsInfoDTO.getLastName(),
                    clientsInfoDTO.getMiddleName(),
                    clientsInfoDTO.getDateOfBirth(),
                    clientsInfoDTO.getGender(),
                    clientsInfoDTO.getStatus(),
                    clientsInfoDTO.getAddress(),
                    clientsInfoDTO.getCity(),
                    clientsInfoDTO.getState(),
                    clientsInfoDTO.getZipCode(),
                    clientsInfoDTO.getNotes()
            );
        }catch (Exception e) {
            logger.error("Failed to update StaffsInfo: {}", e.getMessage(), e);
        }
    }
    public ClientsInfoVO ConvertToClientsInfoVO(ClientsInfo clientsInfo) {
        logger.info("Converting to ClientsInfoVO: {}", clientsInfo.getId());

        ClientsInfoVO clientsInfoVO = new ClientsInfoVO();

        clientsInfoVO.setClientId(clientsInfo.getId());
        clientsInfoVO.setClientFirstName(clientsInfo.getFirstName());
        clientsInfoVO.setClientLastName(clientsInfo.getLastName());
        clientsInfoVO.setClientMiddleName(clientsInfo.getMiddleName());
        clientsInfoVO.setDateOfBirth(clientsInfo.getDateOfBirth());
        clientsInfoVO.setGender(clientsInfo.getGender());
        clientsInfoVO.setStatus(clientsInfo.getStatus());
        clientsInfoVO.setAddress(clientsInfo.getAddress());
        clientsInfoVO.setCity(clientsInfo.getCity());
        clientsInfoVO.setState(clientsInfo.getState());
        clientsInfoVO.setZipCode(clientsInfo.getZipCode());
        clientsInfoVO.setNotes(clientsInfo.getNotes());

        return clientsInfoVO;
    }
    private String emptyIfNull(String s) {
        return s == null ? "" : s;
    }
}
