package com.eduerp.dto;

import com.eduerp.entity.Attendance;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AttendanceDto {
    private Long id;
    private Long studentId;
    private String studentName;
    private String classId;
    private String subject;
    private LocalDate date;
    private String status;
    private LocalDateTime markedAt;

    public static AttendanceDto from(Attendance a) {
        AttendanceDto dto = new AttendanceDto();
        dto.setId(a.getId());
        dto.setClassId(a.getClassId());
        dto.setSubject(a.getSubject());
        dto.setDate(a.getDate());
        dto.setStatus(a.getStatus().name());
        dto.setMarkedAt(a.getMarkedAt());
        if (a.getStudent() != null) {
            dto.setStudentId(a.getStudent().getId());
            dto.setStudentName(a.getStudent().getName());
        }
        return dto;
    }
}
