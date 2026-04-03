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
public class AttendanceService {

    private final AttendanceRepository attendanceRepo;
    private final UserRepository userRepo;

    public List<AttendanceDto> getStudentAttendance(Long studentId) {
        return attendanceRepo.findByStudentIdOrderByDateDesc(studentId)
            .stream().map(AttendanceDto::from).collect(Collectors.toList());
    }

    public void markAttendance(AttendanceRequest req, String teacherEmail) {
        User teacher = userRepo.findByEmail(teacherEmail)
            .orElseThrow(() -> new RuntimeException("Teacher not found"));
        for (AttendanceRequest.StudentAttendanceEntry entry : req.getEntries()) {
            User student = userRepo.findById(entry.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found: " + entry.getStudentId()));
            Attendance attendance = Attendance.builder()
                .student(student)
                .teacher(teacher)
                .date(req.getDate())
                .classId(req.getClassId())
                .subject(req.getSubject())
                .status(Attendance.Status.valueOf(entry.getStatus().toUpperCase()))
                .build();
            attendanceRepo.save(attendance);
        }
    }

    public List<AttendanceDto> getByClass(String classId) {
        return attendanceRepo.findAll().stream()
            .filter(a -> classId.equals(a.getClassId()))
            .map(AttendanceDto::from)
            .collect(Collectors.toList());
    }
}
