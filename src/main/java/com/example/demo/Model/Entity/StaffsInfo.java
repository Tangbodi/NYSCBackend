package com.example.demo.Model.Entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "staffs_info")
public class StaffsInfo {
    @Id
    @Column(name = "staff_id", nullable = false)
    private Long id;

    @Size(max = 31)
    @NotNull
    @Column(name = "username", nullable = false, length = 31)
    private String username;

    @Size(max = 63)
    @NotNull
    @Column(name = "email", nullable = false, length = 63)
    private String email;
    @Size(max = 11)
    @Column(name = "phone", length = 11)
    private String phone;

    @Size(max = 31)
    @NotNull
    @Column(name = "staff_first_name", nullable = false, length = 31)
    private String staffFirstName;

    @Size(max = 31)
    @NotNull
    @Column(name = "staff_last_name", nullable = false, length = 31)
    private String staffLastName;


    @Size(max = 7)
    @NotNull
    @Column(name = "title", nullable = false, length = 7)
    private String title;

    @NotNull
    @Size(max = 1)
    @Column(name = "status", nullable = false, length = 1)
    private String status;

    @Size(max = 31)
    @NotNull
    @Column(name = "staff_middle_name", nullable = false, length = 31)
    private String staffMiddleName;

    @Size(max = 15)
    @Column(name = "employee_type", nullable = false, length = 15)
    private String employeeType;

    @Size(max = 63)
    @NotNull
    @Column(name = "supervisor", nullable = false, length = 63)
    private String supervisor;

    @Size(max = 63)
    @Column(name = "address", length = 63)
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

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;

}