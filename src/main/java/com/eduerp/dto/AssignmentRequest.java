package com.eduerp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AssignmentRequest {
    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private String subject;
    private String classId;
    private String attachmentUrl;
    private LocalDate dueDate;
}
