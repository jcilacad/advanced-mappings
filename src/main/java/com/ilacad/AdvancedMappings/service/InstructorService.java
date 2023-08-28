package com.ilacad.AdvancedMappings.service;

import com.ilacad.AdvancedMappings.dto.InstructorDto;

import java.util.List;

public interface InstructorService {

    void saveInstructor(InstructorDto instructorDto);

    List<InstructorDto> getAllInstructors ();
}
