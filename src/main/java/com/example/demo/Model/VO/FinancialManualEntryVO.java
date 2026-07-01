package com.example.demo.Model.VO;

import lombok.Data;

@Data
public class FinancialManualEntryVO {
    private String entryId;
    private String entryDate;
    private String section;
    private String item;
    private String category;
    private String amount;
    private String note;
    private String createdAt;
    private String updatedAt;
}
