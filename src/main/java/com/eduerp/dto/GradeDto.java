package com.eduerp.dto;

import com.eduerp.entity.Grade;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GradeDto {
    private Long id;
    private Long studentId;
    private String studentName;
    private String subject;
    private String assignmentName;
    private Integer score;
    private Integer maxScore;
    private String finalGrade;
    private LocalDateTime gradedAt;

    public static GradeDto from(Grade g) {
        GradeDto dto = new GradeDto();
        dto.setId(g.getId());
        dto.setSubject(g.getSubject());
        dto.setAssignmentName(g.getAssignmentName());
        dto.setScore(g.getScore());
        dto.setMaxScore(g.getMaxScore());
        dto.setFinalGrade(g.getFinalGrade());
        dto.setGradedAt(g.getGradedAt());
        if (g.getStudent() != null) {
            dto.setStudentId(g.getStudent().getId());
            dto.setStudentName(g.getStudent().getName());
        }
        return dto;
    }
}
