package com.example.demo.Model.VO;

import lombok.Data;

@Data
public class FunderSettingsVO {
    private String funderId;
    private String funderType;
    private String funderName;
    private String address;
    private String coverageType;
    private String vendorId;
    private String phone;
    private String email;
    private String fax;
    private String defaultBillingProvider;
    private String createdAt;
    private String modifiedAt;
}
