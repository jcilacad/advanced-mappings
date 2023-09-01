package com.ilacad.AdvancedMappings.repository;

import com.ilacad.AdvancedMappings.entity.Instructor;
import com.ilacad.AdvancedMappings.entity.InstructorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorDetailRepository extends JpaRepository<InstructorDetail, Long> {
    @Query("SELECT instructor FROM Instructor instructor JOIN FETCH instructor.courses WHERE instructor.id = :id")
    Instructor findInstructorByIdJoinFetch(@Param("id") Long id);
}
