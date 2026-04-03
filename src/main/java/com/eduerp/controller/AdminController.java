package com.eduerp.controller;

import com.eduerp.dto.*;
import com.eduerp.entity.*;
import com.eduerp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AuditLogRepository  auditLogRepo;
    private final ResourceRepository  resourceRepo;
    private final FinanceRepository   financeRepo;
    private final DepartmentRepository deptRepo;
    private final ScheduleRepository  scheduleRepo;
    private final UserRepository      userRepo;

    /* ═══════════════════ AUDIT LOGS ═══════════════════ */

    @GetMapping("/api/admin/audit-logs")
    public ResponseEntity<List<AuditLog>> auditLogs() {
        return ResponseEntity.ok(auditLogRepo.findAllByOrderByTimestampDesc());
    }

    /* ═══════════════════ DASHBOARD STATS ══════════════ */

    @GetMapping("/api/admin/dashboard/stats")
    public ResponseEntity<Map<String, Long>> adminStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("students",  userRepo.countByRole(User.Role.student));
        stats.put("teachers",  userRepo.countByRole(User.Role.teacher));
        stats.put("admins",    userRepo.countByRole(User.Role.admin));
        stats.put("total",     userRepo.count());
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/api/administrator/dashboard/stats")
    public ResponseEntity<Map<String, Object>> administratorStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudents", userRepo.countByRole(User.Role.student));
        stats.put("totalTeachers", userRepo.countByRole(User.Role.teacher));
        stats.put("departments",   deptRepo.count());
        stats.put("resources",     resourceRepo.count());
        return ResponseEntity.ok(stats);
    }

    /* ═══════════════════ RESOURCES ════════════════════ */

    @GetMapping("/api/administrator/resources")
    public ResponseEntity<List<ResourceDto>> resources() {
        return ResponseEntity.ok(resourceRepo.findAll()
            .stream().map(ResourceDto::from).collect(Collectors.toList()));
    }

    @PostMapping("/api/administrator/resources")
    public ResponseEntity<ResourceDto> createResource(@RequestBody ResourceDto dto) {
        Resource r = Resource.builder()
            .name(dto.getName())
            .type(dto.getType())
            .capacity(dto.getCapacity())
            .status(dto.getStatus() != null
                ? Resource.Status.valueOf(dto.getStatus().toUpperCase())
                : Resource.Status.AVAILABLE)
            .build();
        return ResponseEntity.ok(ResourceDto.from(resourceRepo.save(r)));
    }

    @PutMapping("/api/administrator/resources/{id}")
    public ResponseEntity<ResourceDto> updateResource(@PathVariable Long id,
            @RequestBody ResourceDto dto) {
        Resource r = resourceRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Resource not found"));
        if (dto.getName()     != null) r.setName(dto.getName());
        if (dto.getCapacity() != null) r.setCapacity(dto.getCapacity());
        if (dto.getStatus()   != null) r.setStatus(Resource.Status.valueOf(dto.getStatus().toUpperCase()));
        return ResponseEntity.ok(ResourceDto.from(resourceRepo.save(r)));
    }

    @DeleteMapping("/api/administrator/resources/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteResource(@PathVariable Long id) {
        resourceRepo.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Resource deleted", null));
    }

    /* ═══════════════════ FINANCE ═══════════════════════ */

    @GetMapping("/api/administrator/finance")
    public ResponseEntity<List<Finance>> financeAll() {
        return ResponseEntity.ok(financeRepo.findAll());
    }

    @PostMapping("/api/administrator/finance")
    public ResponseEntity<Finance> createFinance(@RequestBody Finance finance) {
        return ResponseEntity.ok(financeRepo.save(finance));
    }

    @PutMapping("/api/administrator/finance/{id}")
    public ResponseEntity<Finance> updateFinance(@PathVariable Long id,
            @RequestBody Finance dto) {
        Finance f = financeRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Finance record not found"));
        if (dto.getFeeStatus()   != null) f.setFeeStatus(dto.getFeeStatus());
        if (dto.getPaidAmount()  != null) f.setPaidAmount(dto.getPaidAmount());
        if (dto.getAmount()      != null) f.setAmount(dto.getAmount());
        if (dto.getDueDate()     != null) f.setDueDate(dto.getDueDate());
        return ResponseEntity.ok(financeRepo.save(f));
    }

    /* ═══════════════════ DEPARTMENTS ═══════════════════ */

    @GetMapping("/api/administrator/departments")
    public ResponseEntity<List<Department>> departments() {
        return ResponseEntity.ok(deptRepo.findAll());
    }

    @PostMapping("/api/administrator/departments")
    public ResponseEntity<Department> createDept(@RequestBody Department dept) {
        return ResponseEntity.ok(deptRepo.save(dept));
    }

    @PutMapping("/api/administrator/departments/{id}")
    public ResponseEntity<Department> updateDept(@PathVariable Long id,
            @RequestBody Department dto) {
        Department d = deptRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Department not found"));
        if (dto.getName()        != null) d.setName(dto.getName());
        if (dto.getCode()        != null) d.setCode(dto.getCode());
        if (dto.getDescription() != null) d.setDescription(dto.getDescription());
        return ResponseEntity.ok(deptRepo.save(d));
    }

    @DeleteMapping("/api/administrator/departments/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDept(@PathVariable Long id) {
        deptRepo.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Department deleted", null));
    }

    /* ═══════════════════ SCHEDULE ═══════════════════════ */

    @GetMapping("/api/student/schedule")
    public ResponseEntity<List<Schedule>> studentSchedule(
            @RequestParam(required = false) String classId) {
        if (classId != null)
            return ResponseEntity.ok(
                scheduleRepo.findByClassIdOrderByDayOfWeekAscTimeSlotAsc(classId));
        return ResponseEntity.ok(scheduleRepo.findAll());
    }

    @GetMapping("/api/teacher/schedule")
    public ResponseEntity<List<Schedule>> teacherSchedule(
            @RequestParam(required = false) Long teacherId) {
        if (teacherId != null)
            return ResponseEntity.ok(
                scheduleRepo.findByTeacherIdOrderByDayOfWeekAscTimeSlotAsc(teacherId));
        return ResponseEntity.ok(scheduleRepo.findAll());
    }

    @PostMapping("/api/admin/schedules")
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        return ResponseEntity.ok(scheduleRepo.save(schedule));
    }

    @DeleteMapping("/api/admin/schedules/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSchedule(@PathVariable Long id) {
        scheduleRepo.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Schedule deleted", null));
    }

    /* ═══════════════════ REPORTS (administrator) ════════ */

    @GetMapping("/api/administrator/reports/summary")
    public ResponseEntity<Map<String, Object>> reports() {
        Map<String, Object> report = new HashMap<>();
        report.put("totalStudents",  userRepo.countByRole(User.Role.student));
        report.put("totalTeachers",  userRepo.countByRole(User.Role.teacher));
        report.put("departments",    deptRepo.count());
        report.put("paidFees",       financeRepo.findByFeeStatus(Finance.FeeStatus.PAID).size());
        report.put("unpaidFees",     financeRepo.findByFeeStatus(Finance.FeeStatus.UNPAID).size());
        report.put("availableRooms", resourceRepo.findByStatus(Resource.Status.AVAILABLE).size());
        return ResponseEntity.ok(report);
    }
}
