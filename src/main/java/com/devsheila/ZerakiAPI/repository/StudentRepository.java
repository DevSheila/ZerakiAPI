package com.devsheila.ZerakiAPI.repository;

import com.devsheila.ZerakiAPI.model.Course;
import com.devsheila.ZerakiAPI.model.Institution;
import com.devsheila.ZerakiAPI.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Page<Student> findByCourseInstitutionIdAndCourseId(Pageable pageable, Long institutionId, Long courseId);

    Page<Student> findByCourseInstitutionId(Pageable pageable, Long institutionId);

    Page<Student> findByCourseId(Pageable pageable, Long courseId);

    Page<Student> findByNameContainingIgnoreCase(Pageable pageable,String name);



}
