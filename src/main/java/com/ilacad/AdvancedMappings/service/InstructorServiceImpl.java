package com.ilacad.AdvancedMappings.service;

import com.ilacad.AdvancedMappings.dto.InstructorDto;
import com.ilacad.AdvancedMappings.entity.Instructor;
import com.ilacad.AdvancedMappings.entity.InstructorDetail;
import com.ilacad.AdvancedMappings.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<InstructorDto> getAllInstructors() {

        List<Instructor> instructorList = instructorRepository.findAll();

        return instructorList.stream()
                .map(instructor -> new InstructorDto(instructor.getId(),
                        instructor.getFirstName(),
                        instructor.getLastName(),
                        instructor.getEmail(),
                        instructor.getInstructorDetail().getYoutubeChannel(),
                        instructor.getInstructorDetail().getHobby()))
                .collect(Collectors.toList());

    }

    @Override
    public boolean isEmailExists(String email) {
        return instructorRepository.existsByEmail(email);
    }
}
