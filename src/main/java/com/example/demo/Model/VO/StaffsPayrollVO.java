package com.example.demo.Model.VO;

import lombok.Data;

@Data
public class StaffsPayrollVO {
    private String staffId;
    private String hourlyRate;
    private String payCode;
    private String effectiveStartDate;
    private String effectiveEndDate;
    private String notes;
    private String createdAt;
    private String modifiedAt;
}
