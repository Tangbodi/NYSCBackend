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

@Getter
@Setter
@Entity
@Table(name = "clients_referring_providers")
public class ClientsReferringProviders {
    @Id
    @Column(name = "referring_provider_id", nullable = false)
    private Long id;
    @NotNull
    @Column(name = "client_id", nullable = false)
    private Long clientId;
    @Size(max = 31)
    @NotNull
    @Column(name = "provider_last_name", nullable = false, length = 31)
    private String providerLastName;

    @Size(max = 31)
    @NotNull
    @Column(name = "provider_first_name", nullable = false, length = 31)
    private String providerFirstName;
    @Size(max = 15)
    @NotNull
    @Column(name = "npi_number", nullable = false, length = 15)
    private String npiNumber;
    @Size(max = 1)
    @NotNull
    @Column(name = "is_active", nullable = false, length = 1)
    private String isActive;
    @Size(max = 31)
    @Column(name = "provider_middle_name", length = 31)
    private String providerMiddleName;
    @NotNull
    @Size(max = 31)
    @Column(name = "taxonomy_code", length = 31)
    private String taxonomyCode;
    @Size(max = 63)
    @NotNull
    @Column(name = "address", nullable = false, length = 63)
    private String address;
    @Size(max = 15)
    @Column(name = "city", length = 15)
    private String city;
    @Size(max = 7)
    @Column(name = "state", length = 7)
    private String state;
    @Size(max = 15)
    @Column(name = "zip_code", length = 15)
    private String zipCode;

    @Size(max = 63)
    @Column(name = "notes", length = 63)
    private String notes;
    @Size(max = 11)
    @Column(name = "phone", length = 11)
    private String phone;
    @Size(max = 15)
    @Column(name = "fax", length = 15)
    private String fax;
    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
