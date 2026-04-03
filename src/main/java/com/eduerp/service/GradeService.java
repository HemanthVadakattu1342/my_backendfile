package com.eduerp.service;

import com.eduerp.dto.*;
import com.eduerp.entity.*;
import com.eduerp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepo;
    private final UserRepository userRepo;
    private final SubmissionRepository submissionRepo;

    public List<GradeDto> getStudentGrades(Long studentId) {
        return gradeRepo.findByStudentIdOrderByGradedAtDesc(studentId)
            .stream().map(GradeDto::from).collect(Collectors.toList());
    }

    public List<GradeDto> getTeacherGrades(Long teacherId) {
        return gradeRepo.findByTeacherIdOrderByGradedAtDesc(teacherId)
            .stream().map(GradeDto::from).collect(Collectors.toList());
    }

    public GradeDto addGrade(GradeRequest req, String teacherEmail) {
        User teacher = userRepo.findByEmail(teacherEmail)
            .orElseThrow(() -> new RuntimeException("Teacher not found"));
        User student = userRepo.findById(req.getStudentId())
            .orElseThrow(() -> new RuntimeException("Student not found"));

        Submission submission = null;
        if (req.getSubmissionId() != null) {
            submission = submissionRepo.findById(req.getSubmissionId()).orElse(null);
            if (submission != null) {
                submission.setStatus(Submission.Status.GRADED);
                submission.setScore(req.getScore());
                submissionRepo.save(submission);
            }
        }

        Grade grade = Grade.builder()
            .student(student)
            .teacher(teacher)
            .submission(submission)
            .subject(req.getSubject())
            .assignmentName(req.getAssignmentName())
            .score(req.getScore())
            .maxScore(req.getMaxScore())
            .finalGrade(req.getFinalGrade())
            .build();
        return GradeDto.from(gradeRepo.save(grade));
    }

    public GradeDto updateGrade(Long id, GradeRequest req) {
        Grade grade = gradeRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Grade not found"));
        if (req.getScore()      != null) grade.setScore(req.getScore());
        if (req.getMaxScore()   != null) grade.setMaxScore(req.getMaxScore());
        if (req.getFinalGrade() != null) grade.setFinalGrade(req.getFinalGrade());
        return GradeDto.from(gradeRepo.save(grade));
    }
}
