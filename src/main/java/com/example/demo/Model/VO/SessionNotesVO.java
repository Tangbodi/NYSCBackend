package com.example.demo.Model.VO;

import lombok.Data;

@Data
public class SessionNotesVO {
    private String sessionId;
    private String template;
    private String purposeOfSession;
    private String clientStatus;
    private String skillStrategies;
    private String behaviorStrategies;
    private String supervisorSupport;
    private String clientResponse;
    private String summaryOfProgress;
    private String lastModifiedBy;
    private String createdAt;
    private String modifiedAt;
}
