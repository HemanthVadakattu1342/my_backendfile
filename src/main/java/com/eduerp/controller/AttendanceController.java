package com.eduerp.controller;

import com.eduerp.dto.*;
import com.eduerp.service.AttendanceService;
import com.eduerp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final UserService userService;

    @GetMapping("/api/student/attendance")
    public ResponseEntity<List<AttendanceDto>> myAttendance(
            @AuthenticationPrincipal UserDetails ud) {
        UserDto user = userService.getMe(ud.getUsername());
        return ResponseEntity.ok(attendanceService.getStudentAttendance(user.getId()));
    }

    @PostMapping("/api/teacher/attendance")
    public ResponseEntity<ApiResponse<Void>> markAttendance(
            @RequestBody AttendanceRequest req,
            @AuthenticationPrincipal UserDetails ud) {
        attendanceService.markAttendance(req, ud.getUsername());
        return ResponseEntity.ok(ApiResponse.ok("Attendance marked successfully", null));
    }

    @GetMapping("/api/teacher/attendance")
    public ResponseEntity<List<AttendanceDto>> classAttendance(
            @RequestParam(required = false) String classId) {
        if (classId != null)
            return ResponseEntity.ok(attendanceService.getByClass(classId));
        return ResponseEntity.ok(List.of());
    }
}
