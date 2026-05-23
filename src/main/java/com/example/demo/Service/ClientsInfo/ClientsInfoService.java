package com.example.demo.Service.ClientsInfo;

import com.example.demo.Model.DTO.ClientsInfoDTO;
import com.example.demo.Model.Entity.ClientsInfo;
import com.example.demo.Model.Entity.EventDetails;
import com.example.demo.Model.VO.ClientsInfoVO;
import com.example.demo.Repository.*;
import com.example.demo.Util.DateTimeConverter;
import com.example.demo.Util.Snowflake;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Long.valueOf;

@Service
public class ClientsInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ClientsInfoService.class);

    @Autowired
    private ClientsInfoRepository clientsInfoRepository;
    @Autowired
    private ClientContactsRepository clientContactsRepository;
    @Autowired
    private ClientFundersRepository clientFundersRepository;
    @Autowired
    private ClientReferringProvidersRepository clientReferringProvidersRepository;
    @Autowired
    private ClientStaffAssignmentsRepository clientStaffAssignmentsRepository;
    @Autowired
    private ClientProgramAssignmentsRepository clientProgramAssignmentsRepository;
    @Autowired
    private EventDetailsRepository eventDetailsRepository;
    @Autowired
    private EventAuditTrailRepository eventAuditTrailRepository;
    @Transactional
    public void RegisterClientsInfo(ClientsInfoDTO clientsInfoDTO) {
        logger.info("Registering ClientsInfo: {}", clientsInfoDTO.getFirstName() + "." + clientsInfoDTO.getLastName());

        try {
            logger.info("Creating UUID for ClientsInfo: {}", clientsInfoDTO.getFirstName());
            Long snowflakeId = Snowflake.generateUniqueId();

            logger.info("Creating ClientsInfo:{}", clientsInfoDTO.getFirstName());
            ClientsInfo clientsInfo = new ClientsInfo();
            clientsInfo.setId(snowflakeId);
            clientsInfo.setClientLastName(clientsInfoDTO.getLastName());
            clientsInfo.setClientFirstName(clientsInfoDTO.getFirstName());
            clientsInfo.setClientMiddleName(clientsInfoDTO.getMiddleName());
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
                logger.info("Found ClientsInfo: {}" + clientsInfo.getClientFirstName() +"."+clientsInfo.getClientLastName());
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
    public List<ClientsInfoVO> GetAllClientsInfo(){
        logger.info("Getting all ClientsInfo: {}");
        try{
            List<ClientsInfo> clientsInfoList = clientsInfoRepository.findAll();
            if(!clientsInfoList.isEmpty()){
                logger.info("Found ClientsInfo.");
                List<ClientsInfoVO> clientsInfoVOList = new ArrayList<>();
                for(ClientsInfo clientsInfo: clientsInfoList){
                    ClientsInfoVO clientsInfoVO = ConvertToClientsInfoVO(clientsInfo);
                    clientsInfoVOList.add(clientsInfoVO);
                }
                return clientsInfoVOList;
            }else{
                logger.info("No ClientsInfo found.");
                return Collections.emptyList();
            }
        }catch (Exception e) {
            logger.error("Failed to get StaffsInfo: {}", e.getMessage(), e);
        }
        return Collections.emptyList();
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
            logger.info("ClientsInfo updated successfully.");
        }catch (Exception e) {
            logger.error("Failed to update ClientsInfo: {}", e.getMessage(), e);
        }
    }
    @Transactional
    public void DeleteClientsInfo(String clientId) {
        logger.info("Deleting client and all related data for clientId: {}", clientId);
        try {
            Long id = Long.valueOf(clientId);
            if (!clientsInfoRepository.existsById(id)) {
                throw new RuntimeException("Client not found for id: " + clientId);
            }

            // 1. Delete event audit trails first (depends on event_details)
            List<EventDetails> events = eventDetailsRepository.findByClientId(id);
            if (!events.isEmpty()) {
                List<Long> eventIds = events.stream()
                        .map(EventDetails::getId)
                        .collect(Collectors.toList());
                eventAuditTrailRepository.deleteByEventIdIn(eventIds);
                logger.info("Deleted audit trails for {} events.", eventIds.size());
            }

            // 2. Delete event details
            eventDetailsRepository.deleteByClientId(id);
            logger.info("Deleted event details for clientId: {}", id);

            // 3. Delete client contacts
            clientContactsRepository.deleteByClientId(id);
            logger.info("Deleted client contacts for clientId: {}", id);

            // 4. Delete client funders
            clientFundersRepository.deleteByClientId(id);
            logger.info("Deleted client funders for clientId: {}", id);

            // 5. Delete client referring providers
            clientReferringProvidersRepository.deleteByClientId(id);
            logger.info("Deleted client referring providers for clientId: {}", id);

            // 6. Delete client staff assignments
            clientStaffAssignmentsRepository.deleteByClientId(id);
            logger.info("Deleted client staff assignments for clientId: {}", id);

            // 7. Delete client program assignments
            clientProgramAssignmentsRepository.deleteByClientId(id);
            logger.info("Deleted client program assignments for clientId: {}", id);

            // 8. Delete the client record itself
            clientsInfoRepository.deleteById(id);
            logger.info("Client {} and all related data deleted successfully.", id);

        } catch (Exception e) {
            logger.error("Failed to delete client {}: {}", clientId, e.getMessage(), e);
            throw e;
        }
    }

    public ClientsInfoVO ConvertToClientsInfoVO(ClientsInfo clientsInfo) {
        logger.info("Converting to ClientsInfoVO: {}", clientsInfo.getId());

        ClientsInfoVO clientsInfoVO = new ClientsInfoVO();

        clientsInfoVO.setClientId(String.valueOf(clientsInfo.getId()));
        clientsInfoVO.setFirstName(clientsInfo.getClientFirstName());
        clientsInfoVO.setLastName(clientsInfo.getClientLastName());
        clientsInfoVO.setMiddleName(clientsInfo.getClientMiddleName());
        clientsInfoVO.setDateOfBirth(clientsInfo.getDateOfBirth());
        clientsInfoVO.setGender(clientsInfo.getGender());
        clientsInfoVO.setStatus(clientsInfo.getStatus());
        clientsInfoVO.setAddress(clientsInfo.getAddress());
        clientsInfoVO.setCity(clientsInfo.getCity());
        clientsInfoVO.setState(clientsInfo.getState());
        clientsInfoVO.setZipCode(clientsInfo.getZipCode());
        clientsInfoVO.setNotes(clientsInfo.getNotes());
        String formattedCreatedDateTime = DateTimeConverter.DateTimeConvertFromInstant(clientsInfo.getCreatedAt());
        String formattedModifiedDateTime = DateTimeConverter.DateTimeConvertFromInstant(clientsInfo.getModifiedAt());
        clientsInfoVO.setCreatedAt(formattedCreatedDateTime);
        clientsInfoVO.setModifiedAt(formattedModifiedDateTime);
        return clientsInfoVO;
    }
    private String emptyIfNull(String s) {
        return s == null ? "" : s;
    }
}
