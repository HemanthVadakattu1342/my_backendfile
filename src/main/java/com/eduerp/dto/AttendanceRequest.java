package com.eduerp.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class AttendanceRequest {
    private String classId;
    private String subject;
    private LocalDate date;
    private List<StudentAttendanceEntry> entries;

    @Data
    public static class StudentAttendanceEntry {
        private Long studentId;
        private String status; // PRESENT, ABSENT, LATE
    }
}
