package com.eduerp.dto;

import com.eduerp.entity.Submission;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SubmissionDto {
    private Long id;
    private Long assignmentId;
    private String assignmentTitle;
    private Long studentId;
    private String studentName;
    private String fileUrl;
    private String comments;
    private String status;
    private Integer score;
    private LocalDateTime submittedAt;

    public static SubmissionDto from(Submission s) {
        SubmissionDto dto = new SubmissionDto();
        dto.setId(s.getId());
        dto.setFileUrl(s.getFileUrl());
        dto.setComments(s.getComments());
        dto.setStatus(s.getStatus().name());
        dto.setScore(s.getScore());
        dto.setSubmittedAt(s.getSubmittedAt());
        if (s.getAssignment() != null) {
            dto.setAssignmentId(s.getAssignment().getId());
            dto.setAssignmentTitle(s.getAssignment().getTitle());
        }
        if (s.getStudent() != null) {
            dto.setStudentId(s.getStudent().getId());
            dto.setStudentName(s.getStudent().getName());
        }
        return dto;
    }
}
