package com.example.demo.Service.CustomProgram;

import com.example.demo.Model.DTO.CustomProgramsDTO;
import com.example.demo.Model.DTO.ProgramTargetDTO;
import com.example.demo.Model.Entity.CustomPrograms;
import com.example.demo.Model.Entity.ProgramTarget;
import com.example.demo.Model.VO.CustomProgramsVO;
import com.example.demo.Model.VO.ProgramTargetVO;
import com.example.demo.Repository.CustomProgramsRepository;
import com.example.demo.Repository.ProgramTargetRepository;
import com.example.demo.Util.DateTimeConverter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomProgramsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomProgramsService.class);

    @Autowired
    private CustomProgramsRepository customProgramsRepository;
    @Autowired
    private ProgramTargetRepository programTargetRepository;

    @Transactional
    public void CreateCustomProgram(CustomProgramsDTO dto) {
        logger.info("Creating CustomProgram: {}", dto.getProgramName());
        try {
            CustomPrograms program = new CustomPrograms();
            program.setLibrary(dto.getLibrary());
            program.setDomain(dto.getDomain());
            program.setProgramName(dto.getProgramName());
            program.setProgramGoal(dto.getProgramGoal());
            program.setObjectiveOne(emptyIfNull(dto.getObjectiveOne()));
            program.setObjectiveTwo(emptyIfNull(dto.getObjectiveTwo()));
            program.setObjectiveThree(emptyIfNull(dto.getObjectiveThree()));
            program.setExercise(emptyIfNull(dto.getExercise()));
            program.setGeneralization(emptyIfNull(dto.getGeneralization()));
            program.setErrorCorrection(emptyIfNull(dto.getErrorCorrection()));
            program.setSupplies(emptyIfNull(dto.getSupplies()));
            program.setTeachingStrategies(emptyIfNull(dto.getTeachingStrategies()));
            program.setTroubleshooting(emptyIfNull(dto.getTroubleshooting()));
            program.setHelpfulHints(emptyIfNull(dto.getHelpfulHints()));
            program.setCreatedAt(Instant.now());
            program.setModifiedAt(Instant.now());

            // Save program first to get the generated program_id
            CustomPrograms saved = customProgramsRepository.save(program);

            // Save each target with the new program_id
            saveTargets(saved.getId(), dto.getTargets());

            logger.info("CustomProgram created successfully with id: {}", saved.getId());
        } catch (Exception e) {
            logger.error("Failed to create CustomProgram: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public CustomProgramsVO GetCustomProgram(String programId) {
        logger.info("Getting CustomProgram: {}", programId);
        try {
            CustomPrograms program = customProgramsRepository.findById(Long.valueOf(programId)).orElse(null);
            if (program != null) {
                List<ProgramTarget> targets = programTargetRepository.findByProgramId(program.getId());
                return ConvertToVO(program, targets);
            } else {
                logger.info("CustomProgram not found.");
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to get CustomProgram: {}", e.getMessage(), e);
        }
        return null;
    }

    public List<CustomProgramsVO> GetAllCustomPrograms() {
        logger.info("Getting all CustomPrograms.");
        try {
            List<CustomPrograms> programs = customProgramsRepository.findAll();
            if (!programs.isEmpty()) {
                logger.info("Found all CustomPrograms.");
                List<CustomProgramsVO> voList = new ArrayList<>();
                for (CustomPrograms program : programs) {
                    List<ProgramTarget> targets = programTargetRepository.findByProgramId(program.getId());
                    voList.add(ConvertToVO(program, targets));
                }
                return voList;
            } else {
                logger.info("No CustomPrograms found.");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            logger.error("Failed to get all CustomPrograms: {}", e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Transactional
    public void UpdateCustomProgram(String programId, CustomProgramsDTO dto) {
        logger.info("Updating CustomProgram: {}", programId);
        try {
            Long id = Long.valueOf(programId);

            // Update scalar fields via native query
            customProgramsRepository.UpdateCustomProgram(
                    id,
                    dto.getLibrary(),
                    dto.getDomain(),
                    dto.getProgramName(),
                    dto.getProgramGoal(),
                    emptyIfNull(dto.getObjectiveOne()),
                    emptyIfNull(dto.getObjectiveTwo()),
                    emptyIfNull(dto.getObjectiveThree()),
                    emptyIfNull(dto.getExercise()),
                    emptyIfNull(dto.getGeneralization()),
                    emptyIfNull(dto.getErrorCorrection()),
                    emptyIfNull(dto.getSupplies()),
                    emptyIfNull(dto.getTeachingStrategies()),
                    emptyIfNull(dto.getTroubleshooting()),
                    emptyIfNull(dto.getHelpfulHints())
            );

            // Replace targets: delete old ones, insert new ones
            programTargetRepository.deleteByProgramId(id);
            saveTargets(id, dto.getTargets());

            logger.info("CustomProgram updated successfully.");
        } catch (Exception e) {
            logger.error("Failed to update CustomProgram: {}", e.getMessage(), e);
            throw e;
        }
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private void saveTargets(Long programId, List<ProgramTargetDTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) return;
        for (ProgramTargetDTO dto : dtoList) {
            ProgramTarget target = new ProgramTarget();
            target.setProgramId(programId);
            target.setTargetName(dto.getTargetName());
            target.setObjective(emptyIfNull(dto.getObjective()));
            target.setStatus(emptyIfNull(dto.getStatus()));
            target.setDateOpened(emptyIfNull(dto.getDateOpened()));
            target.setDateMastered(emptyIfNull(dto.getDateMastered()));
            target.setCreatedAt(Instant.now());
            target.setModifiedAt(Instant.now());
            programTargetRepository.save(target);
        }
    }

    private CustomProgramsVO ConvertToVO(CustomPrograms program, List<ProgramTarget> targets) {
        CustomProgramsVO vo = new CustomProgramsVO();
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
        vo.setTargets(convertTargetEntityList(targets));
        vo.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(program.getCreatedAt()));
        vo.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(program.getModifiedAt()));
        return vo;
    }

    private List<ProgramTargetVO> convertTargetEntityList(List<ProgramTarget> entities) {
        List<ProgramTargetVO> voList = new ArrayList<>();
        if (entities == null) return voList;
        for (ProgramTarget entity : entities) {
            ProgramTargetVO vo = new ProgramTargetVO();
            vo.setTargetId(String.valueOf(entity.getId()));
            vo.setTargetName(entity.getTargetName());
            vo.setObjective(entity.getObjective());
            vo.setStatus(entity.getStatus());
            vo.setDateOpened(entity.getDateOpened());
            vo.setDateMastered(entity.getDateMastered());
            vo.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(entity.getCreatedAt()));
            vo.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(entity.getModifiedAt()));
            voList.add(vo);
        }
        return voList;
    }

    private String emptyIfNull(String s) {
        return s == null ? "" : s;
    }
}
