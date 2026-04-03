package com.eduerp.service;

import com.eduerp.dto.*;
import com.eduerp.entity.User;
import com.eduerp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream().map(UserDto::from).collect(Collectors.toList());
    }

    public List<UserDto> getUsersByRole(String role) {
        User.Role r = User.Role.valueOf(role.trim().toLowerCase());
        return userRepo.findByRole(r).stream().map(UserDto::from).collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        return UserDto.from(userRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserDto createUser(UserDto dto, String rawPassword) {
        if (userRepo.existsByEmail(dto.getEmail()))
            throw new RuntimeException("Email already registered");
        User.Role role = User.Role.valueOf(dto.getRole().trim().toLowerCase());
        User user = User.builder()
            .name(dto.getName())
            .email(dto.getEmail().toLowerCase())
            .password(passwordEncoder.encode(rawPassword != null ? rawPassword : "changeme123"))
            .role(role)
            .status(User.Status.ACTIVE)
            .department(dto.getDepartment())
            .classId(dto.getClassId())
            .enrollmentId(dto.getEnrollmentId())
            .employeeId(dto.getEmployeeId())
            .phone(dto.getPhone())
            .build();
        return UserDto.from(userRepo.save(user));
    }

    public UserDto updateUser(Long id, UserDto dto) {
        User user = userRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        if (dto.getName()       != null) user.setName(dto.getName());
        if (dto.getPhone()      != null) user.setPhone(dto.getPhone());
        if (dto.getDepartment() != null) user.setDepartment(dto.getDepartment());
        if (dto.getClassId()    != null) user.setClassId(dto.getClassId());
        if (dto.getStatus()     != null) user.setStatus(User.Status.valueOf(dto.getStatus().toUpperCase()));
        if (dto.getAvatar()     != null) user.setAvatar(dto.getAvatar());
        return UserDto.from(userRepo.save(user));
    }

    public void toggleStatus(Long id) {
        User user = userRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(user.getStatus() == User.Status.ACTIVE
            ? User.Status.INACTIVE : User.Status.ACTIVE);
        userRepo.save(user);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    public UserDto getMe(String email) {
        return UserDto.from(userRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found")));
    }
}
