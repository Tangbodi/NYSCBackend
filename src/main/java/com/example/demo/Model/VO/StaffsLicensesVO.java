package com.example.demo.Model.VO;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class StaffsLicensesVO {
    private String licenseId;
    private String staffId;
    private String licenseName;
    private String licenseNumber;
    private String licenseState;
    private String issueDate;
    private String expiredDate;
    private String notes;
    private String createdAt;
    private String modifiedAt;
}
