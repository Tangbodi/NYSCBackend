package com.example.demo.Model.VO;

import lombok.Data;

@Data
public class AssignedServiceVO {
    private String serviceId;
    private String billingCode;
    private String ratePerUnit;
    private String unitType;
    private String service;
    private String description;
    private String inactive;
    private String assignedAt;
}
