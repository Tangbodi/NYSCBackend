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
@Table(name = "clients_contacts")
public class ClientsContacts {
    @Id
    @Column(name = "client_id", nullable = false)
    private Long id;
    @Size(max = 31)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 31)
    private String firstName;

    @Size(max = 31)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 31)
    private String lastName;
    @Size(max = 31)
    @NotNull
    @Column(name = "middle_name", nullable = false, length = 31)
    private String middleName;
    @Size(max = 63)
    @NotNull
    @Column(name = "email", nullable = false, length = 63)
    private String email;
    @Size(max = 11)
    @Column(name = "phone", length = 11)
    private String phone;
    @Size(max = 15)
    @NotNull
    @Column(name = "relationship_type", nullable = false, length = 15)
    private String relationshipType;
    @Size(max = 1)
    @NotNull
    @Column(name = "is_primary", nullable = false, length = 1)
    private String isPrimary;
    @Size(max = 63)
    @Column(name = "address", length = 63)
    private String address;
    @Size(max = 15)
    @NotNull
    @Column(name = "city", nullable = false, length = 15)
    private String city;
    @Size(max = 7)
    @NotNull
    @Column(name = "state", nullable = false, length = 7)
    private String state;
    @Size(max = 15)
    @NotNull
    @Column(name = "zip_code", nullable = false, length = 15)
    private String zipCode;

    @Size(max = 63)
    @NotNull
    @Column(name = "notes", nullable = false, length = 63)
    private String notes;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
