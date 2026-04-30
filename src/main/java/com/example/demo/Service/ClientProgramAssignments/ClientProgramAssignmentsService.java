package com.example.demo.Service.ClientProgramAssignments;

import com.example.demo.Model.DTO.ClientProgramAssignmentsDTO;
import com.example.demo.Model.Entity.ClientProgramAssignments;
import com.example.demo.Model.Entity.ClientProgramId;
import com.example.demo.Model.Entity.CustomPrograms;
import com.example.demo.Model.VO.AssignedProgramVO;
import com.example.demo.Model.VO.ClientProgramAssignmentsVO;
import com.example.demo.Repository.ClientProgramAssignmentsRepository;
import com.example.demo.Repository.CustomProgramsRepository;
import com.example.demo.Util.DateTimeConverter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientProgramAssignmentsService {
    private static final Logger logger = LoggerFactory.getLogger(ClientProgramAssignmentsService.class);

    @Autowired
    private ClientProgramAssignmentsRepository clientProgramAssignmentsRepository;
    @Autowired
    private CustomProgramsRepository customProgramsRepository;

    @Transactional
    public void AssignClientToProgram(ClientProgramAssignmentsDTO dto) {
        logger.info("Assigning client {} to program {}", dto.getClientId(), dto.getProgramId());
        try {
            ClientProgramId compositeId = buildId(dto);

            if (clientProgramAssignmentsRepository.existsById(compositeId)) {
                throw new RuntimeException("Client " + dto.getClientId() + " is already assigned to program " + dto.getProgramId() + ".");
            }

            ClientProgramAssignments assignment = new ClientProgramAssignments();
            assignment.setId(compositeId);
            assignment.setCreatedAt(Instant.now());
            assignment.setModifiedAt(Instant.now());
            clientProgramAssignmentsRepository.save(assignment);
            logger.info("Client {} assigned to program {} successfully.", dto.getClientId(), dto.getProgramId());
        } catch (Exception e) {
            logger.error("Failed to assign client to program: {}", e.getMessage(), e);
            throw e;
        }
    }

    public ClientProgramAssignmentsVO GetAssignment(String clientId) {
        logger.info("Getting all program assignments for client: {}", clientId);
        try {
            List<ClientProgramAssignments> assignments = clientProgramAssignmentsRepository
                    .findByIdClientId(Long.valueOf(clientId));

            ClientProgramAssignmentsVO vo = new ClientProgramAssignmentsVO();
            vo.setClientId(clientId);

            List<AssignedProgramVO> programList = new ArrayList<>();
            for (ClientProgramAssignments assignment : assignments) {
                CustomPrograms program = customProgramsRepository
                        .findById(Long.valueOf(assignment.getId().getProgramId())).orElse(null);
                if (program != null) {
                    programList.add(ConvertToAssignedProgramVO(program, assignment));
                } else {
                    logger.warn("Program {} not found for assignment.", assignment.getId().getProgramId());
                }
            }
            vo.setPrograms(programList);
            return vo;
        } catch (Exception e) {
            logger.error("Failed to get assignments for client: {}", e.getMessage(), e);
        }
        return null;
    }

    @Transactional
    public void UnassignProgram(ClientProgramAssignmentsDTO dto) {
        logger.info("Unassigning client {} from program {}", dto.getClientId(), dto.getProgramId());
        try {
            ClientProgramId compositeId = buildId(dto);

            if (!clientProgramAssignmentsRepository.existsById(compositeId)) {
                throw new RuntimeException("No assignment found for client " + dto.getClientId() + " and program " + dto.getProgramId() + ".");
            }
            clientProgramAssignmentsRepository.deleteById(compositeId);
            logger.info("Client {} unassigned from program {} successfully.", dto.getClientId(), dto.getProgramId());
        } catch (Exception e) {
            logger.error("Failed to unassign program: {}", e.getMessage(), e);
            throw e;
        }
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private ClientProgramId buildId(ClientProgramAssignmentsDTO dto) {
        ClientProgramId compositeId = new ClientProgramId();
        compositeId.setClientId(Long.valueOf(dto.getClientId()));
        compositeId.setProgramId(Integer.valueOf(dto.getProgramId()));
        return compositeId;
    }

    private AssignedProgramVO ConvertToAssignedProgramVO(CustomPrograms program, ClientProgramAssignments assignment) {
        AssignedProgramVO vo = new AssignedProgramVO();
        vo.setProgramId(String.valueOf(program.getId()));
        vo.setLibrary(program.getLibrary());
        vo.setDomain(program.getDomain());
        vo.setProgramName(program.getProgramName());
        vo.setProgramGoal(program.getProgramGoal());
        vo.setObjectiveOne(program.getObjectiveOne());
        vo.setObjectiveTwo(program.getObjectiveTwo());
        vo.setObjectiveThree(program.getObjectiveThree());
        vo.setExercise(program.getExercise());
        vo.setGeneralization(program.getGeneralization());
        vo.setErrorCorrection(program.getErrorCorrection());
        vo.setSupplies(program.getSupplies());
        vo.setTeachingStrategies(program.getTeachingStrategies());
        vo.setTroubleshooting(program.getTroubleshooting());
        vo.setHelpfulHints(program.getHelpfulHints());
        vo.setAssignedAt(DateTimeConverter.DateTimeConvertFromInstant(assignment.getCreatedAt()));
        return vo;
    }
}
