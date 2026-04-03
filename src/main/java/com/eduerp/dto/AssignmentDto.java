package com.eduerp.dto;

import com.eduerp.entity.Assignment;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AssignmentDto {
    private Long id;
    private String title;
    private String description;
    private String subject;
    private String classId;
    private String attachmentUrl;
    private Long teacherId;
    private String teacherName;
    private LocalDate dueDate;
    private LocalDateTime createdAt;

    public static AssignmentDto from(Assignment a) {
        AssignmentDto dto = new AssignmentDto();
        dto.setId(a.getId());
        dto.setTitle(a.getTitle());
        dto.setDescription(a.getDescription());
        dto.setSubject(a.getSubject());
        dto.setClassId(a.getClassId());
        dto.setAttachmentUrl(a.getAttachmentUrl());
        dto.setDueDate(a.getDueDate());
        dto.setCreatedAt(a.getCreatedAt());
        if (a.getTeacher() != null) {
            dto.setTeacherId(a.getTeacher().getId());
            dto.setTeacherName(a.getTeacher().getName());
        }
        return dto;
    }
}
