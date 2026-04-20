package com.example.demo.Service.CustomProgram;

import com.example.demo.Model.DTO.CustomProgramDTO;
import com.example.demo.Model.Entity.CustomProgram;
import com.example.demo.Model.VO.CustomProgramVO;
import com.example.demo.Repository.CustomProgramRepository;
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
public class CustomProgramService {
    private static final Logger logger = LoggerFactory.getLogger(CustomProgramService.class);

    @Autowired
    private CustomProgramRepository customProgramRepository;

    @Transactional
    public void CreateCustomProgram(CustomProgramDTO dto) {
        logger.info("Creating CustomProgram: {}", dto.getProgramName());
        try {
            CustomProgram program = new CustomProgram();
            program.setLibrary(dto.getLibrary());
            program.setDomain(dto.getDomain());
            program.setProgramName(dto.getProgramName());
            program.setProgramGoal(dto.getProgramGoal());
            program.setCreatedAt(Instant.now());
            program.setModifiedAt(Instant.now());
            customProgramRepository.save(program);
            logger.info("CustomProgram created successfully.");
        } catch (Exception e) {
            logger.error("Failed to create CustomProgram: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public CustomProgramVO GetCustomProgram(String programId) {
        logger.info("Getting CustomProgram: {}", programId);
        try {
            CustomProgram program = customProgramRepository.findById(Long.valueOf(programId)).orElse(null);
            if (program != null) {
                return ConvertToVO(program);
            } else {
                logger.info("CustomProgram not found.");
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to get CustomProgram: {}", e.getMessage(), e);
        }
        return null;
    }

    public List<CustomProgramVO> GetAllCustomPrograms() {
        logger.info("Getting all CustomPrograms.");
        try {
            List<CustomProgram> programs = customProgramRepository.findAll();
            if (!programs.isEmpty()) {
                List<CustomProgramVO> voList = new ArrayList<>();
                for (CustomProgram program : programs) {
                    voList.add(ConvertToVO(program));
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
    public void UpdateCustomProgram(String programId, CustomProgramDTO dto) {
        logger.info("Updating CustomProgram: {}", programId);
        try {
            customProgramRepository.UpdateCustomProgram(
                    Long.valueOf(programId),
                    dto.getLibrary(),
                    dto.getDomain(),
                    dto.getProgramName(),
                    dto.getProgramGoal()
            );
            logger.info("CustomProgram updated successfully.");
        } catch (Exception e) {
            logger.error("Failed to update CustomProgram: {}", e.getMessage(), e);
        }
    }

    private CustomProgramVO ConvertToVO(CustomProgram program) {
        CustomProgramVO vo = new CustomProgramVO();
        vo.setProgramId(String.valueOf(program.getId()));
        vo.setLibrary(program.getLibrary());
        vo.setDomain(program.getDomain());
        vo.setProgramName(program.getProgramName());
        vo.setProgramGoal(program.getProgramGoal());
        vo.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(program.getCreatedAt()));
        vo.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(program.getModifiedAt()));
        return vo;
    }
}
