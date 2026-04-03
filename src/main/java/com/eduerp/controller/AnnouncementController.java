package com.eduerp.controller;

import com.eduerp.dto.*;
import com.eduerp.service.AnnouncementService;
import com.eduerp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final UserService userService;

    /* ── Student ── */
    @GetMapping("/api/student/announcements")
    public ResponseEntity<List<AnnouncementDto>> studentAnnouncements(
            @AuthenticationPrincipal UserDetails ud) {
        UserDto user = userService.getMe(ud.getUsername());
        return ResponseEntity.ok(announcementService.getForStudent(user.getClassId()));
    }

    /* ── Teacher ── */
    @GetMapping("/api/teacher/announcements")
    public ResponseEntity<List<AnnouncementDto>> teacherAnnouncements() {
        return ResponseEntity.ok(announcementService.getAll());
    }

    @PostMapping("/api/teacher/announcements")
    public ResponseEntity<AnnouncementDto> teacherCreate(
            @RequestBody AnnouncementRequest req,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(announcementService.create(req, ud.getUsername()));
    }

    @DeleteMapping("/api/teacher/announcements/{id}")
    public ResponseEntity<ApiResponse<Void>> teacherDelete(@PathVariable Long id) {
        announcementService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Deleted", null));
    }

    /* ── Admin ── */
    @GetMapping("/api/admin/announcements")
    public ResponseEntity<List<AnnouncementDto>> adminAnnouncements() {
        return ResponseEntity.ok(announcementService.getAll());
    }

    @PostMapping("/api/admin/announcements")
    public ResponseEntity<AnnouncementDto> adminCreate(
            @RequestBody AnnouncementRequest req,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(announcementService.create(req, ud.getUsername()));
    }

    @PutMapping("/api/admin/announcements/{id}")
    public ResponseEntity<AnnouncementDto> adminUpdate(
            @PathVariable Long id,
            @RequestBody AnnouncementRequest req) {
        return ResponseEntity.ok(announcementService.update(id, req));
    }

    @DeleteMapping("/api/admin/announcements/{id}")
    public ResponseEntity<ApiResponse<Void>> adminDelete(@PathVariable Long id) {
        announcementService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Deleted", null));
    }

    /* ── Administrator notices ── */
    @GetMapping("/api/administrator/notices")
    public ResponseEntity<List<AnnouncementDto>> notices() {
        return ResponseEntity.ok(announcementService.getAll());
    }

    @PostMapping("/api/administrator/notices")
    public ResponseEntity<AnnouncementDto> createNotice(
            @RequestBody AnnouncementRequest req,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(announcementService.create(req, ud.getUsername()));
    }

    @DeleteMapping("/api/administrator/notices/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNotice(@PathVariable Long id) {
        announcementService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Deleted", null));
    }
}
