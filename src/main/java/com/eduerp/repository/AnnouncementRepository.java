package com.eduerp.repository;

import com.eduerp.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByClassIdIsNullOrClassIdOrderByCreatedAtDesc(String classId);
    List<Announcement> findAllByOrderByCreatedAtDesc();
    List<Announcement> findByUserIdOrderByCreatedAtDesc(Long userId);
}
