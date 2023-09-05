package com.ilacad.AdvancedMappings.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    @NotEmpty
    @Size(max = 50, message = "The maximum value must be at least 50 characters")
    private String courseName;

    @Size(max = 50, message = "The maximum value must be at least 50 characters")
    private String description;
}
