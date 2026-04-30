package com.example.demo.Model.VO;

import lombok.Data;

@Data
public class ClientFundersVO {
    // client_funders
    private String id;
    private String clientId;
    private String funderId;
    private String insuranceId;

    // clients_info
    private String clientFirstName;
    private String clientLastName;
    private String clientMiddleName;
    private String dateOfBirth;
    private String gender;
    private String status;

    // funder_settings
    private String funderType;
    private String funderName;
    private String funderAddress;
    private String coverageType;
    private String vendorId;
    private String phone;
    private String email;
    private String fax;
    private String defaultBillingProvider;

    private String createdAt;
    private String modifiedAt;
}
