package com.eduerp.controller;

import com.eduerp.dto.*;
import com.eduerp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /* ── Admin: full user management ── */
    @GetMapping("/api/admin/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/api/admin/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/api/admin/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto,
            @RequestParam(defaultValue = "changeme123") String password) {
        return ResponseEntity.ok(userService.createUser(dto, password));
    }

    @PutMapping("/api/admin/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,
            @RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @PatchMapping("/api/admin/users/{id}/toggle-status")
    public ResponseEntity<ApiResponse<Void>> toggleStatus(@PathVariable Long id) {
        userService.toggleStatus(id);
        return ResponseEntity.ok(ApiResponse.ok("Status updated", null));
    }

    @DeleteMapping("/api/admin/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.ok("User deleted", null));
    }

    @GetMapping("/api/admin/users/role/{role}")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    /* ── Any authenticated user: own profile ── */
    @GetMapping("/api/users/me")
    public ResponseEntity<UserDto> getMe(@AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(userService.getMe(ud.getUsername()));
    }

    @PutMapping("/api/users/me")
    public ResponseEntity<UserDto> updateMe(@AuthenticationPrincipal UserDetails ud,
            @RequestBody UserDto dto) {
        UserDto current = userService.getMe(ud.getUsername());
        return ResponseEntity.ok(userService.updateUser(current.getId(), dto));
    }

    /* ── Teacher: list students ── */
    @GetMapping("/api/teacher/students")
    public ResponseEntity<List<UserDto>> getStudents(
            @RequestParam(required = false) String classId) {
        if (classId != null) {
            // filter by class
            return ResponseEntity.ok(userService.getUsersByRole("student").stream()
                .filter(u -> classId.equals(u.getClassId()))
                .collect(java.util.stream.Collectors.toList()));
        }
        return ResponseEntity.ok(userService.getUsersByRole("student"));
    }
}
