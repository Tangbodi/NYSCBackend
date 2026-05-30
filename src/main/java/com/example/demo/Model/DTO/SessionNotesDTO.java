package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class SessionNotesDTO implements Serializable {

    @NotBlank(message = "Session ID is required.")
    private String sessionId;

    @NotBlank(message = "Template is required.")
    @Length(min = 1, max = 63, message = "Template length not eligible.")
    private String template;

    @NotBlank(message = "Purpose of session is required.")
    @Length(min = 1, max = 255, message = "Purpose of session length not eligible.")
    private String purposeOfSession;

    @NotBlank(message = "Client status is required.")
    @Length(min = 1, max = 4095, message = "Client status length not eligible.")
    private String clientStatus;

    @NotBlank(message = "Skill strategies is required.")
    @Length(min = 1, max = 255, message = "Skill strategies length not eligible.")
    private String skillStrategies;

    @NotBlank(message = "Behavior strategies is required.")
    @Length(min = 1, max = 255, message = "Behavior strategies length not eligible.")
    private String behaviorStrategies;

    @NotBlank(message = "Supervisor support is required.")
    @Length(min = 1, max = 63, message = "Supervisor support length not eligible.")
    private String supervisorSupport;

    @NotBlank(message = "Client response is required.")
    @Length(min = 1, max = 4095, message = "Client response length not eligible.")
    private String clientResponse;

    @NotBlank(message = "Summary of progress is required.")
    @Length(min = 1, max = 4095, message = "Summary of progress length not eligible.")
    private String summaryOfProgress;
}
