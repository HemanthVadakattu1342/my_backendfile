package com.eduerp.repository;

import com.eduerp.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentIdOrderByDateDesc(Long studentId);
    List<Attendance> findByClassIdAndDateOrderByStudentId(String classId, LocalDate date);
    List<Attendance> findByTeacherIdAndDateOrderByStudentId(Long teacherId, LocalDate date);
    long countByStudentIdAndStatus(Long studentId, Attendance.Status status);
}
