package com.example.demo.Model.VO;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ClientsFundersVO {
    private String clientId;
    private String payerName;
    private String planName;
    private String memberId;
    private String groupNumber;
    private String relationshipToClient;
    private String policyHolderName;
    private String policyHolderPhone;
    private String policyHolderEmail;
    private String policyHolderAddress;
    private String policyHolderCity;
    private String policyHolderState;
    private String policyHolderZipCode;
    private String coverageOrder;
    private String effectiveStart;
    private String effectiveEnd;
    private String isActive;
    private String notes;
    private String createdAt;
    private String modifiedAt;
}
