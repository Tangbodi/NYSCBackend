//package com.example.demo.Service.Reminder;
//
//import com.example.demo.Model.Entity.ClientContacts;
//import com.example.demo.Model.Entity.EventDetails;
//import com.example.demo.Model.Entity.StaffsInfo;
//import com.example.demo.Repository.ClientContactsRepository;
//import com.example.demo.Repository.EventDetailsRepository;
//import com.example.demo.Repository.StaffsInfoRepository;
//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
//import jakarta.annotation.PostConstruct;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//@Service
//public class ReminderSchedulerService {
//    private static final Logger logger = LoggerFactory.getLogger(ReminderSchedulerService.class);
//    private static final ZoneId NEW_YORK = ZoneId.of("America/New_York");
//    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//    @Value("${twilio.account.sid}")
//    private String accountSid;
//
//    @Value("${twilio.auth.token}")
//    private String authToken;
//
//    @Value("${twilio.phone.number}")
//    private String fromNumber;
//
//    private final EventDetailsRepository eventDetailsRepository;
//    private final StaffsInfoRepository staffsInfoRepository;
//    private final ClientContactsRepository clientContactsRepository;
//
//    public ReminderSchedulerService(EventDetailsRepository eventDetailsRepository,
//                                    StaffsInfoRepository staffsInfoRepository,
//                                    ClientContactsRepository clientContactsRepository) {
//        this.eventDetailsRepository = eventDetailsRepository;
//        this.staffsInfoRepository = staffsInfoRepository;
//        this.clientContactsRepository = clientContactsRepository;
//    }
//
//    @PostConstruct
//    public void initTwilio() {
//        Twilio.init(accountSid, authToken);
//        logger.info("Twilio initialized.");
//    }
//
//    // Runs every day at 6:00 PM New York time
//    @Scheduled(cron = "0 0 18 * * *", zone = "America/New_York")
//    public void sendAppointmentReminders() {
//        String tomorrow = ZonedDateTime.now(NEW_YORK).plusDays(1).format(DATE_FORMAT);
//        logger.info("Running SMS reminder scheduler for tomorrow: {}", tomorrow);
//
//        List<EventDetails> events = eventDetailsRepository.findByDate(tomorrow);
//        if (events.isEmpty()) {
//            logger.info("No appointments found for tomorrow: {}", tomorrow);
//            return;
//        }
//
//        logger.info("Found {} appointment(s) for tomorrow.", events.size());
//        for (EventDetails event : events) {
//            sendStaffSms(event);
//            sendClientSms(event);
//        }
//    }
//
//    private void sendStaffSms(EventDetails event) {
//        try {
//            StaffsInfo staff = staffsInfoRepository.findById(event.getStaffId()).orElse(null);
//            if (staff == null || staff.getPhone() == null || staff.getPhone().isBlank()) {
//                logger.warn("No phone found for staffId: {}", event.getStaffId());
//                return;
//            }
//
//            String body = String.format(
//                "Hi %s, reminder: you have an appointment tomorrow.\n" +
//                "Client: %s\nDate: %s\nTime: %s",
//                staff.getStaffFirstName(),
//                event.getClientName(),
//                event.getDate(),
//                event.getStartTime()
//            );
//
//            sendSms(formatPhone(staff.getPhone()), body);
//            logger.info("SMS reminder sent to staff: {}", staff.getPhone());
//        } catch (Exception e) {
//            logger.error("Failed to send SMS to staff {}: {}", event.getStaffId(), e.getMessage(), e);
//        }
//    }
//
//    private void sendClientSms(EventDetails event) {
//        try {
//            ClientContacts contact = clientContactsRepository.findById(event.getClientId()).orElse(null);
//            if (contact == null || contact.getPhone() == null || contact.getPhone().isBlank()) {
//                logger.warn("No phone found for clientId: {}", event.getClientId());
//                return;
//            }
//
//            String body = String.format(
//                "Hi %s, reminder: %s has an appointment tomorrow.\n" +
//                "Date: %s\nTime: %s\nStaff: %s",
//                contact.getFirstName(),
//                event.getClientName(),
//                event.getDate(),
//                event.getStartTime(),
//                event.getStaffMember()
//            );
//
//            sendSms(formatPhone(contact.getPhone()), body);
//            logger.info("SMS reminder sent to client contact: {}", contact.getPhone());
//        } catch (Exception e) {
//            logger.error("Failed to send SMS to client {}: {}", event.getClientId(), e.getMessage(), e);
//        }
//    }
//
//    private void sendSms(String to, String body) {
//        Message.creator(
//                new PhoneNumber(to),
//                new PhoneNumber(fromNumber),
//                body
//        ).create();
//    }
//
//    // Phone stored as 11 digits (e.g. 15551234567) → E.164 format (+15551234567)
//    private String formatPhone(String phone) {
//        if (phone.startsWith("+")) return phone;
//        return "+" + phone;
//    }
//}
