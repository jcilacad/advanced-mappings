package com.ilacad.AdvancedMappings.service;

import com.ilacad.AdvancedMappings.dto.CourseDto;
import com.ilacad.AdvancedMappings.entity.Course;

import java.util.List;

public interface StudentService {

    List<Course> findAllCourses();

    List<Course> findCourse(CourseDto courseDto);

    Course findCourseByCourseId(Long courseId);
}
