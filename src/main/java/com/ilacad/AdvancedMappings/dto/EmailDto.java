package com.ilacad.AdvancedMappings.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmailDto {

    @Email
    @NotEmpty(message = "Email must not be empty")
    private String email;
}
