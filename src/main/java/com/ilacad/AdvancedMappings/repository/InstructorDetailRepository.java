package com.ilacad.AdvancedMappings.repository;

import com.ilacad.AdvancedMappings.entity.InstructorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorDetailRepository extends JpaRepository<InstructorDetail, Long> {

}
