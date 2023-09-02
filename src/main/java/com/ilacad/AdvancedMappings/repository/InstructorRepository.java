package com.ilacad.AdvancedMappings.repository;

import com.ilacad.AdvancedMappings.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    boolean existsByEmail(String email);

    Optional<Instructor> findByEmail(String email);

    @Query("SELECT i FROM Instructor i " +
            "JOIN FETCH i.courses " +
            "JOIN FETCH i.instructorDetail " + 
            "WHERE i.id = :id")
    Instructor findInstructorByIdJoinFetch(@Param("id") Long id);
}
