package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class FinancialManualEntryDTO implements Serializable {

    private String entryId;

    @NotBlank(message = "Entry date is required.")
    @Length(max = 15, message = "Entry date length not eligible.")
    private String entryDate;

    @NotBlank(message = "Section is required.")
    @Length(max = 512, message = "Section length not eligible.")
    private String section;

    @NotBlank(message = "Item is required.")
    @Length(max = 100, message = "Item length not eligible.")
    private String item;

    @NotBlank(message = "Category is required.")
    @Length(max = 100, message = "Category length not eligible.")
    private String category;

    private String amount;

    private String note;
}
