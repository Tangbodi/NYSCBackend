package com.example.demo.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "staff_license")
public class StaffsLicense {
    @Id
    @Column(name = "staff_license_id", nullable = false)
    private Long id;

    @Column(name = "staff_id", nullable = false)
    private Long staffId;

    @Size(max = 31)
    @Column(name = "license_name", nullable = false, length = 31)
    private String licenseName;
    @Size(max = 63)
    @Column(name = "license_number", nullable = false, length = 63)
    private String licenseNumber;
    @Size(max = 63)
    @NotNull
    @Column(name = "notes", nullable = false, length = 63)
    private String notes;
    @Column(name = "issue_date")
    private LocalDate issueDate;
    @Column(name = "expired_date")
    private LocalDate expiredDate;
    @Column(name = "effective_start_date")
    private LocalDate effectiveStartDate;

    @Column(name = "effective_end_date")
    private String effectiveEndDate;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
