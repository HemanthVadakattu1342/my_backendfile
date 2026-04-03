package com.eduerp.repository;

import com.eduerp.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByClassIdOrderByDueDateAsc(String classId);
    List<Assignment> findByTeacherIdOrderByCreatedAtDesc(Long teacherId);
    List<Assignment> findBySubjectOrderByCreatedAtDesc(String subject);
}
