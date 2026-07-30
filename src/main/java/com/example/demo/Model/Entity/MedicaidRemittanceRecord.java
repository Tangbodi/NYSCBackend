package com.example.demo.Model.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "medicaid_remittance_records")
public class MedicaidRemittanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", nullable = false)
    private Long id;

    @Size(max = 31)
    @NotNull
    @Column(name = "remittance_no", nullable = false, length = 31)
    private String remittanceNo;

    @NotNull
    @Column(name = "remittance_date", nullable = false)
    private LocalDate remittanceDate;

    @Size(max = 15)
    @NotNull
    @Column(name = "cycle", nullable = false, length = 15)
    private String cycle;

    @Size(max = 7)
    @NotNull
    @Column(name = "line_no", nullable = false, length = 7)
    private String lineNo;

    @Size(max = 31)
    @NotNull
    @Column(name = "office_account", nullable = false, length = 31)
    private String officeAccount;

    @Size(max = 63)
    @NotNull
    @Column(name = "client_last_name_pdf", nullable = false, length = 63)
    private String clientLastNamePdf;

    @Size(max = 15)
    @NotNull
    @Column(name = "medicaid_client_id", nullable = false, length = 15)
    private String medicaidClientId;

    @Size(max = 127)
    @Column(name = "client_full_name", length = 127)
    private String clientFullName;

    @Size(max = 31)
    @NotNull
    @Column(name = "tcn", nullable = false, length = 31)
    private String tcn;

    @NotNull
    @Column(name = "date_of_service", nullable = false)
    private LocalDate dateOfService;

    @Size(max = 15)
    @Column(name = "proc_code", length = 15)
    private String procCode;

    @Column(name = "units", precision = 9, scale = 3)
    private BigDecimal units;

    @NotNull
    @Column(name = "charged", nullable = false, precision = 10, scale = 2)
    private BigDecimal charged;

    @NotNull
    @Column(name = "paid", nullable = false, precision = 10, scale = 2)
    private BigDecimal paid;

    @Size(max = 31)
    @NotNull
    @Column(name = "status", nullable = false, length = 31)
    private String status;

    @Column(name = "errors_or_notes", columnDefinition = "TEXT")
    private String errorsOrNotes;

    @NotNull
    @Column(name = "imported_at", nullable = false)
    private Instant importedAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
