package com.eduerp.repository;
import com.eduerp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByClassIdOrderByDayOfWeekAscTimeSlotAsc(String classId);
    List<Schedule> findByTeacherIdOrderByDayOfWeekAscTimeSlotAsc(Long teacherId);
    List<Schedule> findByDayOfWeekAndClassId(String dayOfWeek, String classId);
}
