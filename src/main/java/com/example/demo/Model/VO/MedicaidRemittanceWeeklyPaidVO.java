package com.example.demo.Model.VO;

import lombok.Data;

import java.util.List;

@Data
public class MedicaidRemittanceWeeklyPaidVO {
    private String cycle;
    private String weekStart;
    private String weekEnd;
    private String paidAmount;
    private List<String> remittanceDates;
    private List<ServiceLine> serviceLines;
    private List<BreakdownRow> serviceBreakdown;
    private List<BreakdownRow> clientBreakdown;

    @Data
    public static class ServiceLine {
        private String dateOfService;
        private String clientName;
        private String medicaidClientId;
        private String serviceCode;
        private Integer units;
        private String charged;
        private String paid;
        private String status;
        private String officeAccount;
        private String tcn;
        private String lineNo;
    }

    @Data
    public static class BreakdownRow {
        private String label;
        private String paidAmount;
        private Integer units;
        private Integer lineCount;
    }
}
