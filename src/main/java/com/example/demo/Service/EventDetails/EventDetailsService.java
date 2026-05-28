package com.example.demo.Service.EventDetails;

import com.example.demo.Model.DTO.EventDetailsDTO;
import com.example.demo.Model.DTO.EventUpdateDTO;
import com.example.demo.Model.Entity.EventAuditTrail;
import com.example.demo.Model.Entity.EventDetails;
import com.example.demo.Model.VO.EventAuditTrailVO;
import com.example.demo.Model.VO.EventDetailsVO;
import com.example.demo.Model.Entity.ClientContacts;
import com.example.demo.Model.Entity.StaffsInfo;
import com.example.demo.Repository.ClientContactsRepository;
import com.example.demo.Repository.EventAuditTrailRepository;
import com.example.demo.Repository.EventDetailsRepository;
import com.example.demo.Repository.StaffsInfoRepository;
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
public class EventDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(EventDetailsService.class);

    @Autowired
    private EventDetailsRepository eventDetailsRepository;
    @Autowired
    private ClientContactsRepository clientContactsRepository;
    @Autowired
    private StaffsInfoRepository staffsInfoRepository;
    @Autowired
    private EventAuditTrailRepository eventAuditTrailRepository;

    @Transactional
    public void CreateEvent(EventDetailsDTO dto, Long staffId) {
        logger.info("Creating EventDetails for client: {}", dto.getClientId());
        try {
            EventDetails event = new EventDetails();
            Long snowflakeId = System.currentTimeMillis();
            event.setId(snowflakeId);
            event.setClientId(Long.valueOf(dto.getClientId()));
            event.setStaffId(Long.valueOf(dto.getStaffId()));
            event.setType(dto.getType());
            event.setDate(dto.getDate());
            event.setStartTime(dto.getStartTime());
            event.setEndTime(dto.getEndTime());
            event.setPayCode(dto.getPayCode());
            event.setClientName(dto.getClientName());
            event.setStaffMember(dto.getStaffMember());
            event.setService(dto.getService());
            String phone = "";
            ClientContacts contact = clientContactsRepository.findById(Long.valueOf(dto.getClientId())).orElse(null);
            if (contact != null && contact.getPhone() != null) {
                phone = contact.getPhone();
            } else {
                logger.warn("No phone found in client_contacts for clientId: {}", dto.getClientId());
            }
            event.setClientContactReminders(phone);
            event.setPlaceOfService(dto.getPlaceOfService());
            String lastModifiedBy = resolveStaffName(staffId);
            event.setLastModifiedBy(lastModifiedBy);
            event.setTag(emptyIfNull(dto.getTag()));
            event.setStaffReminders(emptyIfNull(dto.getStaffReminders()));
            event.setVerifications(emptyIfNull(dto.getVerifications()));
            event.setCancellations(emptyIfNull(dto.getCancellations()));
            event.setCreatedAt(Instant.now());
            event.setModifiedAt(Instant.now());
            eventDetailsRepository.save(event);
            logger.info("EventDetails created successfully.");
        } catch (Exception e) {
            logger.error("Failed to create EventDetails: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void UpdateEvent(String eventId, EventUpdateDTO eventUpdateDTO, Long staffId) {
        logger.info("Updating EventDetails: {}", eventId);
        try {
            Long id = Long.valueOf(eventId);
            EventDetails existing = eventDetailsRepository.findById(id).orElse(null);
            if (existing == null) {
                throw new RuntimeException("Event not found for id: " + eventId);
            }

            String modifiedBy = resolveStaffName(staffId);
            Instant now = Instant.now();

            // Record audit trail for every changed field
            recordAuditTrail(existing, eventUpdateDTO, id, modifiedBy, now);

            // Persist the update
            eventDetailsRepository.UpdateEventDetails(
                    id,
                    eventUpdateDTO.getType(),
                    eventUpdateDTO.getDate(),
                    eventUpdateDTO.getStartTime(),
                    eventUpdateDTO.getEndTime(),
                    eventUpdateDTO.getPayCode(),
                    eventUpdateDTO.getClientName(),
                    eventUpdateDTO.getStaffMember(),
                    eventUpdateDTO.getService(),
                    eventUpdateDTO.getPlaceOfService(),
                    modifiedBy,
                    emptyIfNull(eventUpdateDTO.getTag()),
                    emptyIfNull(eventUpdateDTO.getStaffReminders()),
                    emptyIfNull(eventUpdateDTO.getVerifications()),
                    emptyIfNull(eventUpdateDTO.getCancellations()),
                    now
            );
            logger.info("EventDetails updated successfully.");
        } catch (Exception e) {
            logger.error("Failed to update EventDetails: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<EventDetailsVO> GetEventsByClientId(String clientId) {
        logger.info("Getting EventDetails for client: {}", clientId);
        try {
            List<EventDetails> events = eventDetailsRepository.findByClientId(Long.valueOf(clientId));
            if (!events.isEmpty()) {
                List<EventDetailsVO> voList = new ArrayList<>();
                for (EventDetails event : events) {
                    voList.add(ConvertToVO(event));
                }
                return voList;
            } else {
                logger.info("No EventDetails found for client: {}", clientId);
                return Collections.emptyList();
            }
        } catch (Exception e) {
            logger.error("Failed to get EventDetails: {}", e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    public List<EventDetailsVO> GetEventsByStaffId(String staffId) {
        logger.info("Getting EventDetails for staff: {}", staffId);
        try {
            List<EventDetails> events = eventDetailsRepository.findByStaffId(Long.valueOf(staffId));
            if (!events.isEmpty()) {
                List<EventDetailsVO> voList = new ArrayList<>();
                for (EventDetails event : events) {
                    voList.add(ConvertToVO(event));
                }
                return voList;
            } else {
                logger.info("No EventDetails found for staff: {}", staffId);
                return Collections.emptyList();
            }
        } catch (Exception e) {
            logger.error("Failed to get EventDetails by staff: {}", e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Transactional
    public void DeleteEvent(String eventId) {
        logger.info("Deleting EventDetails: {}", eventId);
        try {
            Long id = Long.valueOf(eventId);
            if (!eventDetailsRepository.existsById(id)) {
                throw new RuntimeException("Event not found for id: " + eventId);
            }
            eventDetailsRepository.deleteById(id);
            logger.info("EventDetails deleted successfully.");
        } catch (Exception e) {
            logger.error("Failed to delete EventDetails: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<EventAuditTrailVO> GetAuditTrail(String eventId) {
        logger.info("Getting audit trail for event: {}", eventId);
        try {
            List<EventAuditTrail> trails = eventAuditTrailRepository
                    .findByEventIdOrderByModifiedAtDesc(Long.valueOf(eventId));
            if (!trails.isEmpty()) {
                List<EventAuditTrailVO> voList = new ArrayList<>();
                for (EventAuditTrail trail : trails) {
                    voList.add(ConvertAuditToVO(trail));
                }
                return voList;
            } else {
                logger.info("No audit trail found for event: {}", eventId);
                return Collections.emptyList();
            }
        } catch (Exception e) {
            logger.error("Failed to get audit trail: {}", e.getMessage(), e);
            throw e;
        }
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private void recordAuditTrail(EventDetails existing, EventUpdateDTO dto,
                                  Long eventId, String modifiedBy, Instant now) {
        List<EventAuditTrail> trails = new ArrayList<>();

        checkAndAdd(trails, eventId, "type",            existing.getType(),                     dto.getType(),                     modifiedBy, now);
        checkAndAdd(trails, eventId, "date",            existing.getDate(),                     dto.getDate(),                     modifiedBy, now);
        checkAndAdd(trails, eventId, "startTime",       existing.getStartTime(),                dto.getStartTime(),                modifiedBy, now);
        checkAndAdd(trails, eventId, "endTime",         existing.getEndTime(),                  dto.getEndTime(),                  modifiedBy, now);
        checkAndAdd(trails, eventId, "payCode",         existing.getPayCode(),                  dto.getPayCode(),                  modifiedBy, now);
        checkAndAdd(trails, eventId, "clientName",      existing.getClientName(),               dto.getClientName(),               modifiedBy, now);
        checkAndAdd(trails, eventId, "staffMember",     existing.getStaffMember(),              dto.getStaffMember(),              modifiedBy, now);
        checkAndAdd(trails, eventId, "service",         existing.getService(),                  dto.getService(),                  modifiedBy, now);
        checkAndAdd(trails, eventId, "placeOfService",  existing.getPlaceOfService(),           dto.getPlaceOfService(),           modifiedBy, now);
        checkAndAdd(trails, eventId, "tag",             emptyIfNull(existing.getTag()),         emptyIfNull(dto.getTag()),         modifiedBy, now);
        checkAndAdd(trails, eventId, "staffReminders",  emptyIfNull(existing.getStaffReminders()), emptyIfNull(dto.getStaffReminders()), modifiedBy, now);
        checkAndAdd(trails, eventId, "verifications",   emptyIfNull(existing.getVerifications()), emptyIfNull(dto.getVerifications()), modifiedBy, now);
        checkAndAdd(trails, eventId, "cancellations",   emptyIfNull(existing.getCancellations()), emptyIfNull(dto.getCancellations()), modifiedBy, now);

        if (!trails.isEmpty()) {
            eventAuditTrailRepository.saveAll(trails);
            logger.info("Saved {} audit trail record(s) for event: {}", trails.size(), eventId);
        } else {
            logger.info("No field changes detected for event: {}", eventId);
        }
    }

    private void checkAndAdd(List<EventAuditTrail> trails, Long eventId,
                             String fieldName, String oldVal, String newVal,
                             String modifiedBy, Instant now) {
        String safeOld = emptyIfNull(oldVal);
        String safeNew = emptyIfNull(newVal);
        if (!safeOld.equals(safeNew)) {
            EventAuditTrail trail = new EventAuditTrail();
            trail.setEventId(eventId);
            trail.setFieldName(fieldName);
            trail.setOldValue(safeOld);
            trail.setNewValue(safeNew);
            trail.setModifiedBy(modifiedBy);
            trail.setModifiedAt(now);
            trails.add(trail);
        }
    }

    private String resolveStaffName(Long staffId) {
        StaffsInfo staff = staffsInfoRepository.findById(staffId).orElse(null);
        if (staff != null) {
            return staff.getStaffFirstName() + " " + staff.getStaffLastName();
        }
        logger.warn("Staff not found for staffId: {}", staffId);
        return "";
    }

    private EventDetailsVO ConvertToVO(EventDetails event) {
        EventDetailsVO vo = new EventDetailsVO();
        vo.setEventId(String.valueOf(event.getId()));
        vo.setClientId(String.valueOf(event.getClientId()));
        vo.setStaffId(String.valueOf(event.getStaffId()));
        vo.setType(event.getType());
        vo.setDate(event.getDate());
        vo.setStartTime(event.getStartTime());
        vo.setEndTime(event.getEndTime());
        vo.setPayCode(event.getPayCode());
        vo.setClientName(event.getClientName());
        vo.setStaffMember(event.getStaffMember());
        vo.setService(event.getService());
        vo.setClientContactReminders(event.getClientContactReminders());
        vo.setPlaceOfService(event.getPlaceOfService());
        vo.setLastModifiedBy(event.getLastModifiedBy());
        vo.setTag(event.getTag());
        vo.setStaffReminders(event.getStaffReminders());
        vo.setVerifications(event.getVerifications());
        vo.setCancellations(event.getCancellations());
        vo.setCreatedAt(DateTimeConverter.DateTimeConvertFromInstant(event.getCreatedAt()));
        vo.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(event.getModifiedAt()));
        return vo;
    }

    private EventAuditTrailVO ConvertAuditToVO(EventAuditTrail trail) {
        EventAuditTrailVO vo = new EventAuditTrailVO();
        vo.setAuditId(String.valueOf(trail.getId()));
        vo.setEventId(String.valueOf(trail.getEventId()));
        vo.setFieldName(trail.getFieldName());
        vo.setOldValue(trail.getOldValue());
        vo.setNewValue(trail.getNewValue());
        vo.setModifiedBy(trail.getModifiedBy());
        vo.setModifiedAt(DateTimeConverter.DateTimeConvertFromInstant(trail.getModifiedAt()));
        return vo;
    }

    private String emptyIfNull(String s) {
        return s == null ? "" : s;
    }
}
