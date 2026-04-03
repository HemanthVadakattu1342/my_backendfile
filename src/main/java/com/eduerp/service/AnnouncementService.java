package com.eduerp.service;

import com.eduerp.dto.*;
import com.eduerp.entity.*;
import com.eduerp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepo;
    private final UserRepository userRepo;

    public List<AnnouncementDto> getAll() {
        return announcementRepo.findAllByOrderByCreatedAtDesc()
            .stream().map(AnnouncementDto::from).collect(Collectors.toList());
    }

    public List<AnnouncementDto> getForStudent(String classId) {
        // Return global announcements (classId null) AND class-specific ones
        return announcementRepo.findAllByOrderByCreatedAtDesc().stream()
            .filter(a -> a.getClassId() == null || a.getClassId().equals(classId))
            .map(AnnouncementDto::from)
            .collect(Collectors.toList());
    }

    public AnnouncementDto create(AnnouncementRequest req, String email) {
        User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Announcement a = Announcement.builder()
            .title(req.getTitle())
            .body(req.getBody())
            .classId(req.getClassId())
            .user(user)
            .postedBy(user.getName())
            .build();
        return AnnouncementDto.from(announcementRepo.save(a));
    }

    public AnnouncementDto update(Long id, AnnouncementRequest req) {
        Announcement a = announcementRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Announcement not found"));
        if (req.getTitle() != null) a.setTitle(req.getTitle());
        if (req.getBody()  != null) a.setBody(req.getBody());
        return AnnouncementDto.from(announcementRepo.save(a));
    }

    public void delete(Long id) {
        announcementRepo.deleteById(id);
    }
}
