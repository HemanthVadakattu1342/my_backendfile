package com.eduerp.dto;

import com.eduerp.entity.Resource;
import lombok.Data;

@Data
public class ResourceDto {
    private Long id;
    private String name;
    private String type;
    private Integer capacity;
    private String status;

    public static ResourceDto from(Resource r) {
        ResourceDto dto = new ResourceDto();
        dto.setId(r.getId());
        dto.setName(r.getName());
        dto.setType(r.getType());
        dto.setCapacity(r.getCapacity());
        dto.setStatus(r.getStatus().name());
        return dto;
    }
}
