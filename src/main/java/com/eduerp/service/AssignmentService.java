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
public class AssignmentService {

    private final AssignmentRepository assignmentRepo;
    private final SubmissionRepository submissionRepo;
    private final UserRepository userRepo;

    public List<AssignmentDto> getForStudent(String classId) {
        return assignmentRepo.findByClassIdOrderByDueDateAsc(classId)
            .stream().map(AssignmentDto::from).collect(Collectors.toList());
    }

    public List<AssignmentDto> getAllAssignments() {
        return assignmentRepo.findAll()
            .stream().map(AssignmentDto::from).collect(Collectors.toList());
    }

    public List<AssignmentDto> getForTeacher(Long teacherId) {
        return assignmentRepo.findByTeacherIdOrderByCreatedAtDesc(teacherId)
            .stream().map(AssignmentDto::from).collect(Collectors.toList());
    }

    public AssignmentDto create(AssignmentRequest req, String teacherEmail) {
        User teacher = userRepo.findByEmail(teacherEmail)
            .orElseThrow(() -> new RuntimeException("Teacher not found"));
        Assignment a = Assignment.builder()
            .title(req.getTitle())
            .description(req.getDescription())
            .subject(req.getSubject())
            .classId(req.getClassId())
            .attachmentUrl(req.getAttachmentUrl())
            .dueDate(req.getDueDate())
            .teacher(teacher)
            .build();
        return AssignmentDto.from(assignmentRepo.save(a));
    }

    public AssignmentDto update(Long id, AssignmentRequest req) {
        Assignment a = assignmentRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Assignment not found"));
        if (req.getTitle()       != null) a.setTitle(req.getTitle());
        if (req.getDescription() != null) a.setDescription(req.getDescription());
        if (req.getSubject()     != null) a.setSubject(req.getSubject());
        if (req.getDueDate()     != null) a.setDueDate(req.getDueDate());
        return AssignmentDto.from(assignmentRepo.save(a));
    }

    public void delete(Long id) {
        assignmentRepo.deleteById(id);
    }

    public SubmissionDto submit(Long assignmentId, SubmissionRequest req, String studentEmail) {
        User student = userRepo.findByEmail(studentEmail)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        Assignment assignment = assignmentRepo.findById(assignmentId)
            .orElseThrow(() -> new RuntimeException("Assignment not found"));
        Submission sub = submissionRepo
            .findByAssignmentIdAndStudentId(assignmentId, student.getId())
            .orElse(Submission.builder().assignment(assignment).student(student).build());
        sub.setFileUrl(req.getFileUrl());
        sub.setComments(req.getComments());
        sub.setStatus(Submission.Status.SUBMITTED);
        return SubmissionDto.from(submissionRepo.save(sub));
    }

    public List<SubmissionDto> getMySubmissions(String studentEmail) {
        User student = userRepo.findByEmail(studentEmail)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        return submissionRepo.findByStudentIdOrderBySubmittedAtDesc(student.getId())
            .stream().map(SubmissionDto::from).collect(Collectors.toList());
    }

    public List<SubmissionDto> getSubmissionsForTeacher(Long teacherId) {
        return submissionRepo.findByAssignmentTeacherIdOrderBySubmittedAtDesc(teacherId)
            .stream().map(SubmissionDto::from).collect(Collectors.toList());
    }

    public List<SubmissionDto> getSubmissionsForAssignment(Long assignmentId) {
        return submissionRepo.findByAssignmentIdOrderBySubmittedAtDesc(assignmentId)
            .stream().map(SubmissionDto::from).collect(Collectors.toList());
    }
}
