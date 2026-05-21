package com.example.demo.Model.VO;

import lombok.Data;

@Data
public class EventAuditTrailVO {
    private String auditId;
    private String eventId;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private String modifiedBy;
    private String modifiedAt;
}
