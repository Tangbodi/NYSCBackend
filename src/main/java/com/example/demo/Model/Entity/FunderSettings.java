package com.example.demo.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "funder_settings")
public class FunderSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funder_id", nullable = false)
    private Integer id;

    @Size(max = 15)
    @NotNull
    @Column(name = "funder_type", nullable = false, length = 15)
    private String funderType;

    @Size(max = 31)
    @NotNull
    @Column(name = "funder_name", nullable = false, length = 31)
    private String funderName;

    @Size(max = 63)
    @NotNull
    @Column(name = "address", nullable = false, length = 63)
    private String address;

    @Size(max = 31)
    @NotNull
    @Column(name = "coverage_type", nullable = false, length = 31)
    private String coverageType;

    @Size(max = 15)
    @Column(name = "vendor_id", length = 15)
    private String vendorId;

    @Size(max = 15)
    @Column(name = "phone", length = 15)
    private String phone;

    @Size(max = 63)
    @Column(name = "email", length = 63)
    private String email;

    @Size(max = 15)
    @Column(name = "fax", length = 15)
    private String fax;

    @Size(max = 63)
    @Column(name = "default_billing_provider", length = 63)
    private String defaultBillingProvider;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
