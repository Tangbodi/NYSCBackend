package com.example.demo.Model.VO;

import lombok.Data;

@Data
public class EventDetailsVO {
    private String eventId;
    private String clientId;
    private String type;
    private String date;
    private String startTime;
    private String endTime;
    private String payCode;
    private String clientName;
    private String staffMember;
    private String service;
    private String clientContactReminders;
    private String placeOfService;
    private String lastModifiedBy;
    private String tag;
    private String staffReminders;
    private String verifications;
    private String cancellations;
    private String createdAt;
    private String modifiedAt;
}
