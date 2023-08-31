package com.ilacad.AdvancedMappings.repository;

import com.ilacad.AdvancedMappings.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findByInstructor_Id(Long id);
}
