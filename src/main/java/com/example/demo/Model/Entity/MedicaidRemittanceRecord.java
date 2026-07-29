package com.example.demo.Model.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "medicaid_remittance_records")
public class MedicaidRemittanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", nullable = false)
    private Long id;

    @Size(max = 15)
    @Column(name = "cycle", length = 15)
    private String cycle;

    @Size(max = 15)
    @Column(name = "week_start", length = 15)
    private String weekStart;

    @Size(max = 15)
    @Column(name = "week_end", length = 15)
    private String weekEnd;

    @Size(max = 15)
    @Column(name = "remittance_date", length = 15)
    private String remittanceDate;

    @Size(max = 15)
    @NotNull
    @Column(name = "date_of_service", nullable = false, length = 15)
    private String dateOfService;

    @Size(max = 127)
    @Column(name = "client_name", length = 127)
    private String clientName;

    @Size(max = 31)
    @Column(name = "medicaid_client_id", length = 31)
    private String medicaidClientId;

    @Size(max = 15)
    @Column(name = "service_code", length = 15)
    private String serviceCode;

    @Column(name = "units")
    private Integer units;

    @Column(name = "charged", precision = 12, scale = 2)
    private BigDecimal charged;

    @Column(name = "paid", precision = 12, scale = 2)
    private BigDecimal paid;

    @Size(max = 15)
    @Column(name = "status", length = 15)
    private String status;

    @Size(max = 63)
    @Column(name = "office_account", length = 63)
    private String officeAccount;

    @Size(max = 63)
    @Column(name = "tcn", length = 63)
    private String tcn;

    @Size(max = 15)
    @Column(name = "line_no", length = 15)
    private String lineNo;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
