package com.ilacad.AdvancedMappings.service;

import com.ilacad.AdvancedMappings.dto.CourseDto;
import com.ilacad.AdvancedMappings.dto.InstructorDto;
import com.ilacad.AdvancedMappings.entity.Course;
import com.ilacad.AdvancedMappings.entity.Instructor;

import java.util.List;

public interface InstructorService {

    void saveInstructor(InstructorDto instructorDto);

    void updateInstructor(InstructorDto instructorDto);

    List<Instructor> getAllInstructors();

    boolean isEmailExists(String email);

    Long getIdByEmail(String email);

    Instructor findInstructorById(Long id);

    void deleteInstructorById(Long id);

    void deleteOtherDetailsById(Long id);

    void addCourse(Long id, CourseDto courseDto);

    Course findCourseById(Long courseId);

    void deleteCourseById(Long id);

    void updateCourse(Long courseId, CourseDto courseDto);

    void addReview(Long instructorId, Long courseId) ;
}
