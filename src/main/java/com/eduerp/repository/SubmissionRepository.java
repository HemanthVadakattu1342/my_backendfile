package com.eduerp.repository;

import com.eduerp.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByStudentIdOrderBySubmittedAtDesc(Long studentId);
    List<Submission> findByAssignmentIdOrderBySubmittedAtDesc(Long assignmentId);
    Optional<Submission> findByAssignmentIdAndStudentId(Long assignmentId, Long studentId);
    List<Submission> findByAssignmentTeacherIdOrderBySubmittedAtDesc(Long teacherId);
}
