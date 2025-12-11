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
@Table(name = "clients_funders")
public class ClientsFunders {
    @Id
    @Column(name = "client_funder_id", nullable = false)
    private Long id;
    @NotNull
    @Column(name = "client_id", nullable = false)
    private Long clientId;
    @Size(max = 31)
    @NotNull
    @Column(name = "payer_name", nullable = false, length = 31)
    private String payerName;

    @Size(max = 31)
    @Column(name = "member_id", length = 31)
    private String memberId;
    @Size(max = 31)
    @Column(name = "group_number", length = 31)
    private String groupNumber;
    @Size(max = 15)
    @NotNull
    @Column(name = "relationship_to_client", nullable = false, length = 15)
    private String relationshipToClient;
    @NotNull
    @Size(max = 31)
    @Column(name = "policy_holder_name", nullable = false, length = 31)
    private String policyHolderName;
    @Size(max = 11)
    @Column(name = "policy_holder_phone", length = 11)
    private String policyHolderPhone;
    @Size(max = 63)
    @Column(name = "policy_holder_email", length = 63)
    private String policyHolderEmail;
    @Size(max = 63)
    @Column(name = "policy_holder_address", length = 63)
    private String policyHolderAddress;
    @Size(max = 15)
    @Column(name = "policy_holder_city", length = 15)
    private String policyHolderCity;
    @Size(max = 7)
    @Column(name = "policy_holder_state", length = 7)
    private String policyHolderState;
    @Size(max = 15)
    @Column(name = "policy_holder_zip_code", length = 15)
    private String policyHolderZipCode;
    @Size(max = 63)
    @Column(name = "coverage_order", length = 63)
    private String coverageOrder;
    @Size(max = 15)
    @Column(name = "effective_start", length = 15)
    private String effectiveStart;
    @Size(max = 15)
    @Column(name = "effective_end", length = 15)
    private String effectiveEnd;

    @Size(max = 1)
    @Column(name = "is_active", nullable = false, length = 1)
    private String isActive;
    @Size(max = 63)
    @Column(name = "notes", length = 63)
    private String notes;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
