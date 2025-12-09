package com.example.demo.Service.ClientStaffAssignments;

import com.example.demo.Model.DTO.ClientStaffAssignmentsDTO;
import com.example.demo.Model.Entity.ClientStaffAssignments;
import com.example.demo.Model.Entity.ClientStaffId;
import com.example.demo.Repository.ClientStaffAssignmentsRepository;
import com.example.demo.Service.StaffsInfo.StaffsInfoService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ClientStaffAssignmentsService {
    private static final Logger logger = LoggerFactory.getLogger(ClientStaffAssignmentsService.class);

    @Autowired
    private ClientStaffAssignmentsRepository clientStaffAssignmentsRepository;

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
}

