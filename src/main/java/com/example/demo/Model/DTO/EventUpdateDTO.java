package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class EventUpdateDTO implements Serializable {

    @NotBlank(message = "Type is required.")
    @Length(min = 1, max = 15, message = "Type length not eligible.")
    private String type;

    @NotBlank(message = "Date is required.")
    @Length(min = 1, max = 15, message = "Date length not eligible.")
    private String date;

    @NotBlank(message = "Start time is required.")
    @Length(min = 1, max = 15, message = "Start time length not eligible.")
    private String startTime;

    @NotBlank(message = "End time is required.")
    @Length(min = 1, max = 15, message = "End time length not eligible.")
    private String endTime;

    @NotBlank(message = "Pay code is required.")
    @Length(min = 1, max = 15, message = "Pay code length not eligible.")
    private String payCode;

    @NotBlank(message = "Client name is required.")
    @Length(min = 1, max = 63, message = "Client name length not eligible.")
    private String clientName;

    @NotBlank(message = "Staff member is required.")
    @Length(min = 1, max = 63, message = "Staff member length not eligible.")
    private String staffMember;

    @NotBlank(message = "Service is required.")
    @Length(min = 1, max = 127, message = "Service length not eligible.")
    private String service;

    @NotBlank(message = "Place of service is required.")
    @Length(min = 1, max = 63, message = "Place of service length not eligible.")
    private String placeOfService;

    @Length(max = 15, message = "Tag length not eligible.")
    private String tag;

    @Length(max = 63, message = "Staff reminders length not eligible.")
    private String staffReminders;

    @Length(max = 15, message = "Verifications length not eligible.")
    private String verifications;

    @Length(max = 15, message = "Cancellations length not eligible.")
    private String cancellations;
}
