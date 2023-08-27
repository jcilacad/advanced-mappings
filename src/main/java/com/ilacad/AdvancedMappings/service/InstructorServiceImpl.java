package com.ilacad.AdvancedMappings.service;

import com.ilacad.AdvancedMappings.dto.InstructorDto;
import com.ilacad.AdvancedMappings.entity.Instructor;
import com.ilacad.AdvancedMappings.entity.InstructorDetail;
import com.ilacad.AdvancedMappings.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImpl implements InstructorService{

    InstructorRepository instructorRepository;

    @Autowired
    public InstructorServiceImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public void saveInstructor(InstructorDto instructorDto) {

        // Get each value from instructor DTO
        String firstName = instructorDto.getFirstName();
        String lastName = instructorDto.getLastName();
        String email = instructorDto.getEmail();
        String youtubeChannel = instructorDto.getYoutubeChannel();
        String hobby = instructorDto.getHobby();

        // Create a new instance of instructor detail and instructor
        InstructorDetail instructorDetail = new InstructorDetail(youtubeChannel, hobby);
        Instructor instructor = new Instructor(firstName, lastName, email);
        instructor.setInstructorDetail(instructorDetail);

        // Save the instance of instructor
        instructorRepository.save(instructor);
    }
}
