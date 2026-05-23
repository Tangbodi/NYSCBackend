package com.example.demo.Service.ClientStaffAssignments;

import com.example.demo.Model.DTO.ClientStaffAssignmentsDTO;
import com.example.demo.Model.Entity.ClientStaffAssignments;
import com.example.demo.Model.Entity.ClientStaffId;
import com.example.demo.Model.Entity.StaffsInfo;
import com.example.demo.Model.VO.ClientStaffAssignmentsVO;
import com.example.demo.Model.VO.ClientsInfoVO;
import com.example.demo.Model.VO.StaffsInfoVO;
import com.example.demo.Repository.ClientStaffAssignmentsRepository;
import com.example.demo.Service.ClientsInfo.ClientsInfoService;
import com.example.demo.Service.StaffsInfo.StaffsInfoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.Instant;
import java.util.*;

@Service
public class ClientStaffAssignmentsService {
    private static final Logger logger = LoggerFactory.getLogger(ClientStaffAssignmentsService.class);
    private static final String CLIENT = "client";
    private static final String STAFF = "staff";
    @Autowired
    private ClientStaffAssignmentsRepository clientStaffAssignmentsRepository;
    @Autowired
    private ClientsInfoService clientsInfoService;
    @Autowired
    private StaffsInfoService staffsInfoService;
    @Transactional
    public void CreateClientStaffAssignments(ClientStaffAssignmentsDTO clientStaffAssignmentsDTO){
        logger.info("Creating ClientStaffAssignments: {}", clientStaffAssignmentsDTO.getClientId() + "&"+clientStaffAssignmentsDTO.getStaffId());
        try {
            ClientStaffId clientStaffId = new ClientStaffId();
            ClientStaffAssignments clientStaffAssignments = new ClientStaffAssignments();
            clientStaffId.setClientId(Long.valueOf(clientStaffAssignmentsDTO.getClientId()));
            clientStaffId.setStaffId(Long.valueOf(clientStaffAssignmentsDTO.getStaffId()));
            clientStaffAssignments.setId(clientStaffId);
            clientStaffAssignments.setCreatedAt(Instant.now());
            clientStaffAssignments.setModifiedAt(Instant.now());
            clientStaffAssignmentsRepository.save(clientStaffAssignments);
            logger.info("ClientStaffAssignments created successfully.");
        }
        catch (Exception e) {
            logger.error("Failed to create ClientStaffAssignments: {}", e.getMessage(), e);
            throw e;
        }
    }
    @Transactional
    public void RemoveClientStaffAssignment(ClientStaffAssignmentsDTO dto) {
        logger.info("Removing ClientStaffAssignment: client={} staff={}", dto.getClientId(), dto.getStaffId());
        try {
            ClientStaffId id = new ClientStaffId();
            id.setClientId(Long.valueOf(dto.getClientId()));
            id.setStaffId(Long.valueOf(dto.getStaffId()));
            if (!clientStaffAssignmentsRepository.existsById(id)) {
                throw new RuntimeException("Assignment not found.");
            }
            clientStaffAssignmentsRepository.deleteById(id);
            logger.info("ClientStaffAssignment removed successfully.");
        } catch (Exception e) {
            logger.error("Failed to remove ClientStaffAssignment: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Map<String, Object>> GetAllClientStaffAssignments(HttpServletRequest request){
        logger.info("Getting All ClientStaffAssignments: {}");
        //find all clients
        List<ClientsInfoVO> clientsInfoVOList = clientsInfoService.GetAllClientsInfo();
        List<Map<String, Object>> res = new ArrayList<>();

        //clients IDs
        for(ClientsInfoVO clientsInfoVO : clientsInfoVOList){
            Map<String,Object> assignments = new HashMap<>();
            List<StaffsInfoVO> staffsInfoVOList = new ArrayList<>();
            Long clientId = Long.valueOf(clientsInfoVO.getClientId());
                List<Map<String, Object>> staffIdsList = clientStaffAssignmentsRepository.findAllStaffIdsByClientId(clientId);
                for (Map<String, Object> staffIds : staffIdsList) {
                    String staffId = String.valueOf(staffIds.get("staff_id"));
                    StaffsInfoVO staffsInfoVO = staffsInfoService.GetStaffsInfo(staffId, request);
                    staffsInfoVOList.add(staffsInfoVO);
                }

            assignments.put(CLIENT,clientsInfoVO);
            assignments.put(STAFF,staffsInfoVOList);
            res.add(assignments);
        }
        return res;
    }
}

