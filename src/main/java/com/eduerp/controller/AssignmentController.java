package com.eduerp.controller;

import com.eduerp.dto.*;
import com.eduerp.service.AssignmentService;
import com.eduerp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final UserService userService;

    /* ── Student ── */
    @GetMapping("/api/student/assignments")
    public ResponseEntity<List<AssignmentDto>> studentAssignments(
            @AuthenticationPrincipal UserDetails ud) {
        UserDto user = userService.getMe(ud.getUsername());
        String classId = user.getClassId() != null ? user.getClassId() : "";
        return ResponseEntity.ok(assignmentService.getForStudent(classId));
    }

    @PostMapping("/api/student/assignments/{id}/submit")
    public ResponseEntity<SubmissionDto> submit(
            @PathVariable Long id,
            @RequestBody SubmissionRequest req,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(assignmentService.submit(id, req, ud.getUsername()));
    }

    @GetMapping("/api/student/submissions")
    public ResponseEntity<List<SubmissionDto>> mySubmissions(
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(assignmentService.getMySubmissions(ud.getUsername()));
    }

    /* ── Teacher ── */
    @GetMapping("/api/teacher/assignments")
    public ResponseEntity<List<AssignmentDto>> teacherAssignments(
            @AuthenticationPrincipal UserDetails ud) {
        UserDto user = userService.getMe(ud.getUsername());
        return ResponseEntity.ok(assignmentService.getForTeacher(user.getId()));
    }

    @PostMapping("/api/teacher/assignments")
    public ResponseEntity<AssignmentDto> create(
            @Valid @RequestBody AssignmentRequest req,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(assignmentService.create(req, ud.getUsername()));
    }

    @PutMapping("/api/teacher/assignments/{id}")
    public ResponseEntity<AssignmentDto> update(
            @PathVariable Long id,
            @RequestBody AssignmentRequest req) {
        return ResponseEntity.ok(assignmentService.update(id, req));
    }

    @DeleteMapping("/api/teacher/assignments/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        assignmentService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Assignment deleted", null));
    }

    @GetMapping("/api/teacher/submissions")
    public ResponseEntity<List<SubmissionDto>> teacherSubmissions(
            @AuthenticationPrincipal UserDetails ud) {
        UserDto user = userService.getMe(ud.getUsername());
        return ResponseEntity.ok(assignmentService.getSubmissionsForTeacher(user.getId()));
    }

    @GetMapping("/api/teacher/assignments/{id}/submissions")
    public ResponseEntity<List<SubmissionDto>> submissionsForAssignment(
            @PathVariable Long id) {
        return ResponseEntity.ok(assignmentService.getSubmissionsForAssignment(id));
    }
}
