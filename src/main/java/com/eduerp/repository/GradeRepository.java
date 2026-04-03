package com.eduerp.repository;

import com.eduerp.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentIdOrderByGradedAtDesc(Long studentId);
    List<Grade> findByTeacherIdOrderByGradedAtDesc(Long teacherId);
    List<Grade> findByStudentIdAndSubject(Long studentId, String subject);
}
