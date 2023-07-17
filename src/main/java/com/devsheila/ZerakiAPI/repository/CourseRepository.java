package com.devsheila.ZerakiAPI.repository;

import com.devsheila.ZerakiAPI.model.Course;
import com.devsheila.ZerakiAPI.model.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByName(String name);

    Optional<Course> findByNameAndInstitutionId(String name, Long institutionId);


    Optional<Course> findByIdAndInstitutionId(Long courseId, Long institutionId);

    List<Course> findAllByInstitutionId(Long institutionId);

    List<Course> findByNameContainingIgnoreCase(String name);

    List<Course> findAllByOrderByNameAsc();

    List<Course> findAllByOrderByNameDesc();

    Page<Course> findByInstitutionId(Long institutionId, Pageable pageable);

    Page<Course> findByInstitutionIdAndNameContainingIgnoreCase(Long institutionId, String name, Pageable pageable);
}
