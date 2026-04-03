package com.eduerp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AnnouncementRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String body;
    private String classId; // null = broadcast to all
}
