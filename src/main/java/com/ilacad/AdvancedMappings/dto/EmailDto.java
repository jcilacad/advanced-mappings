package com.ilacad.AdvancedMappings.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmailDto {

    @Email
    @NotEmpty(message = "Invalid, you must input a value!")
    private String email;
}
