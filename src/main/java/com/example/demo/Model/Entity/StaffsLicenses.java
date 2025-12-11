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
@Table(name = "staffs_licenses")
public class StaffsLicenses {
    @Id
    @Column(name = "staff_license_id", nullable = false)
    private Long id;
    @NotNull
    @Column(name = "staff_id", nullable = false)
    private Long staffId;
    @Size(max = 31)
    @Column(name = "license_name", length = 31)
    private String licenseName;
    @Size(max = 63)
    @Column(name = "license_number", length = 63)
    private String licenseNumber;
    @Size(max = 7)
    @Column(name = "license_state", length = 7)
    private String licenseState;
    @Size(max = 63)
    @Column(name = "notes", length = 63)
    private String notes;
    @Column(name = "issue_date")
    private String issueDate;
    @Column(name = "expired_date")
    private String expiredDate;
    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
