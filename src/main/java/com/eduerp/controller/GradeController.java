package com.eduerp.controller;

import com.eduerp.dto.*;
import com.eduerp.service.GradeService;
import com.eduerp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;
    private final UserService userService;

    @GetMapping("/api/student/grades")
    public ResponseEntity<List<GradeDto>> myGrades(
            @AuthenticationPrincipal UserDetails ud) {
        UserDto user = userService.getMe(ud.getUsername());
        return ResponseEntity.ok(gradeService.getStudentGrades(user.getId()));
    }

    @GetMapping("/api/teacher/grades")
    public ResponseEntity<List<GradeDto>> teacherGrades(
            @AuthenticationPrincipal UserDetails ud) {
        UserDto user = userService.getMe(ud.getUsername());
        return ResponseEntity.ok(gradeService.getTeacherGrades(user.getId()));
    }

    @PostMapping("/api/teacher/grades")
    public ResponseEntity<GradeDto> addGrade(
            @RequestBody GradeRequest req,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(gradeService.addGrade(req, ud.getUsername()));
    }

    @PutMapping("/api/teacher/grades/{id}")
    public ResponseEntity<GradeDto> updateGrade(
            @PathVariable Long id,
            @RequestBody GradeRequest req) {
        return ResponseEntity.ok(gradeService.updateGrade(id, req));
    }
}
