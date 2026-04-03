package com.eduerp.dto;

import lombok.Data;

@Data
public class GradeRequest {
    private Long studentId;
    private Long submissionId;
    private String subject;
    private String assignmentName;
    private Integer score;
    private Integer maxScore;
    private String finalGrade;
}
