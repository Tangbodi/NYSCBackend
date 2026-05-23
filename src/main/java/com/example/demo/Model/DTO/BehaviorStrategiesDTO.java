package com.example.demo.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class BehaviorStrategiesDTO implements Serializable {

    @NotBlank(message = "Behavior strategy is required.")
    @Length(min = 1, max = 63, message = "Behavior strategy length not eligible.")
    private String behaviorStrategy;
}
