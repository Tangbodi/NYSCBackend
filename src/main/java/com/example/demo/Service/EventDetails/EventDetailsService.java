package com.example.demo.Service.EventDetails;

import com.example.demo.Model.DTO.EventDetailsDTO;
import com.example.demo.Model.Entity.EventDetails;
import com.example.demo.Model.VO.EventDetailsVO;
import com.example.demo.Model.Entity.ClientContacts;
import com.example.demo.Model.Entity.StaffsInfo;
import com.example.demo.Repository.ClientContactsRepository;
import com.example.demo.Repository.EventDetailsRepository;
import com.example.demo.Repository.StaffsInfoRepository;
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

@Service
public class EventDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(EventDetailsService.class);

    @Autowired
    private EventDetailsRepository eventDetailsRepository;
    @Autowired
    private ClientContactsRepository clientContactsRepository;
    @Autowired
    private StaffsInfoRepository staffsInfoRepository;

    @Transactional
    public void CreateEvent(EventDetailsDTO dto, Long staffId) {
        logger.info("Creating EventDetails for client: {}", dto.getClientId());
        try {
            EventDetails event = new EventDetails();
            Long snowflakeId = Snowflake.generateUniqueId();
            event.setId(snowflakeId);
            event.setClientId(Long.valueOf(dto.getClientId()));
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
            String lastModifiedBy = "";
            StaffsInfo staff = staffsInfoRepository.findById(staffId).orElse(null);
            if (staff != null) {
                lastModifiedBy = staff.getStaffFirstName() + " " + staff.getStaffLastName();
            } else {
                logger.warn("Staff not found for staffId: {}", staffId);
            }
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

    // ── helpers ──────────────────────────────────────────────────────────────

    private EventDetailsVO ConvertToVO(EventDetails event) {
        EventDetailsVO vo = new EventDetailsVO();
        vo.setEventId(String.valueOf(event.getId()));
        vo.setClientId(String.valueOf(event.getClientId()));
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

    private String emptyIfNull(String s) {
        return s == null ? "" : s;
    }
}
