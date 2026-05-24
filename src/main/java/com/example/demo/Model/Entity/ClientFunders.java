package com.example.demo.Model.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "client_funders")
public class ClientFunders {

    @EmbeddedId
    private ClientFunderId id;

    @Size(max = 15)
    @NotNull
    @Column(name = "insurance_id", nullable = false, length = 15)
    private String insuranceId;

    @Size(max = 63)
    @NotNull
    @Column(name = "relationship", nullable = false, length = 63)
    private String relationship;

    @Size(max = 15)
    @Column(name = "start_date", length = 15)
    private String startDate;

    @Size(max = 15)
    @Column(name = "end_date", length = 15)
    private String endDate;

    @Size(max = 31)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 31)
    private String firstName;

    @Size(max = 31)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 31)
    private String lastName;

    @Size(max = 9)
    @NotNull
    @Column(name = "coverage_type", nullable = false, length = 9)
    private String coverageType;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
