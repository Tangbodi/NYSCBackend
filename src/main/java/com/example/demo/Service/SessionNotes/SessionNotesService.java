package com.example.demo.Service.SessionNotes;

import com.example.demo.Model.DTO.SessionNotesDTO;
import com.example.demo.Model.Entity.SessionNotes;
import com.example.demo.Model.Entity.StaffsInfo;
import com.example.demo.Model.VO.SessionNotesVO;
import com.example.demo.Repository.SessionNotesRepository;
import com.example.demo.Repository.StaffsInfoRepository;
import com.example.demo.Util.DateTimeConverter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SessionNotesService {
    private static final Logger logger = LoggerFactory.getLogger(SessionNotesService.class);

    @Autowired
    private SessionNotesRepository sessionNotesRepository;
    @Autowired
    private StaffsInfoRepository staffsInfoRepository;

    @Transactional
    public String CreateSessionNote(SessionNotesDTO dto, Long staffId) {
        logger.info("Creating session note.");
        try {
            SessionNotes note = new SessionNotes();
            note.setId(dto.getSessionId());
            note.setTemplate(dto.getTemplate());
            note.setPurposeOfSession(dto.getPurposeOfSession());
            note.setClientStatus(dto.getClientStatus());
            note.setSkillStrategies(dto.getSkillStrategies());
            note.setBehaviorStrategies(dto.getBehaviorStrategies());
            note.setSupervisorSupport(dto.getSupervisorSupport());
            note.setClientResponse(dto.getClientResponse());
            note.setSummaryOfProgress(dto.getSummaryOfProgress());
            note.setLastModifiedBy(resolveStaffName(staffId));
            note.setCreatedAt(Instant.now());
            note.setModifiedAt(Instant.now());
            SessionNotes saved = sessionNotesRepository.save(note);
            logger.info("Session note created successfully with id: {}", saved.getId());
            return String.valueOf(saved.getId());
        } catch (Exception e) {
            logger.error("Failed to create session note: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void UpdateSessionNote(String sessionId, SessionNotesDTO dto, Long staffId) {
        logger.info("Updating session note: {}", sessionId);
        try {
            Long id = Long.valueOf(sessionId);
            if (!sessionNotesRepository.existsById(id)) {
                throw new RuntimeException("Session note not found for id: " + sessionId);
            }
            sessionNotesRepository.UpdateSessionNotes(
                    id,
                    dto.getTemplate(),
                    dto.getPurposeOfSession(),
                    dto.getClientStatus(),
                    dto.getSkillStrategies(),
                    dto.getBehaviorStrategies(),
                    dto.getSupervisorSupport(),
                    dto.getClientResponse(),
                    dto.getSummaryOfProgress(),
                    resolveStaffName(staffId),
                    Instant.now()
            );
            logger.info("Session note updated successfully.");
        } catch (Exception e) {
            logger.error("Failed to update session note: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void DeleteSessionNote(String sessionId) {
        logger.info("Deleting session note: {}", sessionId);
        try {
            Long id = Long.valueOf(sessionId);
            if (!sessionNotesRepository.existsById(id)) {
                throw new RuntimeException("Session note not found for id: " + sessionId);
            }
            sessionNotesRepository.deleteById(id);
            logger.info("Session note deleted successfully.");
        } catch (Exception e) {
            logger.error("Failed to delete session note: {}", e.getMessage(), e);
            throw e;
        }
    }

    public SessionNotesVO GetSessionNote(String sessionId) {
        logger.info("Getting session note: {}", sessionId);
        try {
            SessionNotes note = sessionNotesRepository.findById(Long.valueOf(sessionId)).orElse(null);
            if (note == null) {
                logger.info("Session note not found for id: {}", sessionId);
                return null;
            }
            return ConvertToVO(note);
        } catch (Exception e) {
            logger.error("Failed to get session note: {}", e.getMessage(), e);
            throw e;
        }
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private String resolveStaffName(Long staffId) {
        StaffsInfo staff = staffsInfoRepository.findById(staffId).orElse(null);
        if (staff != null) {
            return staff.getStaffFirstName() + " " + staff.getStaffLastName();
        }
        logger.warn("Staff not found for staffId: {}", staffId);
        return "";
    }

    private SessionNotesVO ConvertToVO(SessionNotes note) {
        SessionNotesVO vo = new SessionNotesVO();
        vo.setSessionId(String.valueOf(note.getId()));
        vo.setTemplate(note.getTemplate());
        vo.setPurposeOfSession(note.getPurposeOfSession());
        vo.setClientStatus(note.getClientStatus());
        vo.setSkillStrategies(note.getSkillStrategies());
        vo.setBehaviorStrategies(note.getBehaviorStrategies());
        vo.setSupervisorSupport(note.getSupervisorSupport());
        vo.setClientResponse(note.getClientResponse());
        vo.setSummaryOfProgress(note.getSummaryOfProgress());
        vo.setLastModifiedBy(note.getLastModifiedBy());
        vo.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(note.getCreatedAt()));
        vo.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(note.getModifiedAt()));
        return vo;
    }
}
