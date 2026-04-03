package com.eduerp.dto;

import com.eduerp.entity.User;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String status;
    private String enrollmentId;
    private String employeeId;
    private String classId;
    private String department;
    private String phone;
    private String avatar;
    private LocalDateTime createdAt;

    public static UserDto from(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        dto.setStatus(user.getStatus().name());
        dto.setEnrollmentId(user.getEnrollmentId());
        dto.setEmployeeId(user.getEmployeeId());
        dto.setClassId(user.getClassId());
        dto.setDepartment(user.getDepartment());
        dto.setPhone(user.getPhone());
        dto.setAvatar(user.getAvatar());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
