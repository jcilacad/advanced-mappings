package com.ilacad.AdvancedMappings.repository;

import com.ilacad.AdvancedMappings.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    boolean existsByEmail(String email);
}
