package com.eduerp.dto;

import com.eduerp.entity.Announcement;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AnnouncementDto {
    private Long id;
    private String title;
    private String body;
    private String postedBy;
    private String classId;
    private LocalDateTime createdAt;

    public static AnnouncementDto from(Announcement a) {
        AnnouncementDto dto = new AnnouncementDto();
        dto.setId(a.getId());
        dto.setTitle(a.getTitle());
        dto.setBody(a.getBody());
        dto.setPostedBy(a.getPostedBy());
        dto.setClassId(a.getClassId());
        dto.setCreatedAt(a.getCreatedAt());
        return dto;
    }
}
