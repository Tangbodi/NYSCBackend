package com.example.demo.Model.VO;

import lombok.Data;

import java.util.List;

@Data
public class MedicaidRemittanceWeeklyPaidVO {
    private String cycle;
    private String paidAmount;
    private List<String> remittanceDates;
    private List<ServiceLine> serviceLines;
    private List<BreakdownRow> serviceBreakdown;
    private List<BreakdownRow> clientBreakdown;

    @Data
    public static class ServiceLine {
        private String remittanceNo;
        private String remittanceDate;
        private String lineNo;
        private String officeAccount;
        private String clientLastNamePdf;
        private String medicaidClientId;
        private String clientFullName;
        private String tcn;
        private String dateOfService;
        private String procCode;
        private String units;
        private String charged;
        private String paid;
        private String status;
        private String errorsOrNotes;
    }

    @Data
    public static class BreakdownRow {
        private String label;
        private String paidAmount;
        private String units;
        private Integer lineCount;
    }
}
