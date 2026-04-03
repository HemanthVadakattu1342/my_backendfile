package com.eduerp.controller;

import com.eduerp.dto.*;
import com.eduerp.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    /* ── Student ── */
    @GetMapping("/api/student/messages")
    public ResponseEntity<List<MessageDto>> studentInbox(
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(messageService.getMyMessages(ud.getUsername()));
    }

    @PostMapping("/api/student/messages")
    public ResponseEntity<MessageDto> studentSend(
            @RequestBody MessageRequest req,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(messageService.send(req, ud.getUsername()));
    }

    @GetMapping("/api/student/messages/{userId}/conversation")
    public ResponseEntity<List<MessageDto>> studentConversation(
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(messageService.getConversation(userId, ud.getUsername()));
    }

    @PatchMapping("/api/student/messages/{id}/read")
    public ResponseEntity<ApiResponse<Void>> studentMarkRead(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails ud) {
        messageService.markRead(id, ud.getUsername());
        return ResponseEntity.ok(ApiResponse.ok("Marked as read", null));
    }

    /* ── Teacher ── */
    @GetMapping("/api/teacher/messages")
    public ResponseEntity<List<MessageDto>> teacherInbox(
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(messageService.getMyMessages(ud.getUsername()));
    }

    @PostMapping("/api/teacher/messages")
    public ResponseEntity<MessageDto> teacherSend(
            @RequestBody MessageRequest req,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(messageService.send(req, ud.getUsername()));
    }

    @GetMapping("/api/teacher/messages/{userId}/conversation")
    public ResponseEntity<List<MessageDto>> teacherConversation(
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(messageService.getConversation(userId, ud.getUsername()));
    }

    @PatchMapping("/api/teacher/messages/{id}/read")
    public ResponseEntity<ApiResponse<Void>> teacherMarkRead(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails ud) {
        messageService.markRead(id, ud.getUsername());
        return ResponseEntity.ok(ApiResponse.ok("Marked as read", null));
    }
}
