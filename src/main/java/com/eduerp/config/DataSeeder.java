package com.eduerp.config;

import com.eduerp.entity.*;
import com.eduerp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository       userRepo;
    private final ResourceRepository   resourceRepo;
    private final DepartmentRepository deptRepo;
    private final ScheduleRepository   scheduleRepo;
    private final PasswordEncoder      passwordEncoder;

    @Override
    public void run(String... args) {
        seedUsers();
        seedDepartments();
        seedResources();
        seedSchedules();
        System.out.println("✅ EduERP seed data loaded.");
    }

    private void seedUsers() {
        createUser("Admin User",         "admin@eduerp.com",         "admin123",
                   User.Role.admin, null, null, null, null);
        createUser("Administrator User", "administrator@eduerp.com", "admin123",
                   User.Role.administrator, null, null, null, null);
        createUser("Aarav Kumar",        "student@eduerp.com",       "student123",
                   User.Role.student, "STU001", "101", "Computer Science", null);
        createUser("Bhavna Singh",       "bhavna@eduerp.com",        "student123",
                   User.Role.student, "STU002", "101", "Computer Science", null);
        createUser("Yuki Tanaka",        "yuki@eduerp.com",          "student123",
                   User.Role.student, "STU003", "102", "Electronics", null);
        createUser("Dr. Rajesh Kumar",   "teacher@eduerp.com",       "teacher123",
                   User.Role.teacher, null, null, "Computer Science", "TEA001");
        createUser("Prof. Meena Gupta",  "meena@eduerp.com",         "teacher123",
                   User.Role.teacher, null, null, "Computer Science", "TEA002");
        createUser("Dr. Vikram Singh",   "vikram@eduerp.com",        "teacher123",
                   User.Role.teacher, null, null, "Electronics", "TEA003");
    }

    private void createUser(String name, String email, String password,
                            User.Role role, String enrollId, String classId,
                            String dept, String empId) {
        if (!userRepo.existsByEmail(email)) {
            userRepo.save(User.builder()
                .name(name).email(email)
                .password(passwordEncoder.encode(password))
                .role(role).status(User.Status.ACTIVE)
                .enrollmentId(enrollId).classId(classId)
                .department(dept).employeeId(empId)
                .build());
        }
    }

    private void seedDepartments() {
        createDept("Computer Science",   "CSE", "CS Department");
        createDept("Electronics",        "ECE", "Electronics & Comm");
        createDept("Mechanical",         "ME",  "Mechanical Engineering");
        createDept("Civil Engineering",  "CE",  "Civil Engineering");
    }

    private void createDept(String name, String code, String desc) {
        if (!deptRepo.existsByName(name)) {
            deptRepo.save(Department.builder()
                .name(name).code(code).description(desc).build());
        }
    }

    private void seedResources() {
        if (resourceRepo.count() == 0) {
            resourceRepo.save(Resource.builder().name("Computer Lab A")   .type("Lab")      .capacity(30) .status(Resource.Status.AVAILABLE)  .build());
            resourceRepo.save(Resource.builder().name("Computer Lab B")   .type("Lab")      .capacity(25) .status(Resource.Status.OCCUPIED)    .build());
            resourceRepo.save(Resource.builder().name("Electronics Lab")  .type("Lab")      .capacity(20) .status(Resource.Status.AVAILABLE)  .build());
            resourceRepo.save(Resource.builder().name("Library Main Hall").type("Library")  .capacity(100).status(Resource.Status.AVAILABLE)  .build());
            resourceRepo.save(Resource.builder().name("Seminar Room 1")   .type("Classroom").capacity(50) .status(Resource.Status.OCCUPIED)    .build());
            resourceRepo.save(Resource.builder().name("Auditorium")       .type("Hall")     .capacity(500).status(Resource.Status.AVAILABLE)  .build());
            resourceRepo.save(Resource.builder().name("Projector 1")      .type("Equipment").capacity(1)  .status(Resource.Status.AVAILABLE)  .build());
        }
    }

    private void seedSchedules() {
        if (scheduleRepo.count() == 0) {
            userRepo.findByEmail("teacher@eduerp.com").ifPresent(teacher -> {
                scheduleRepo.save(Schedule.builder().dayOfWeek("monday")   .timeSlot("09:00-10:00").subject("Data Structures").room("Lab 101") .classId("101").teacher(teacher).build());
                scheduleRepo.save(Schedule.builder().dayOfWeek("monday")   .timeSlot("10:15-11:15").subject("Web Development") .room("Room 203").classId("101").teacher(teacher).build());
                scheduleRepo.save(Schedule.builder().dayOfWeek("tuesday")  .timeSlot("09:00-10:00").subject("Algorithms")      .room("Room 204").classId("101").teacher(teacher).build());
                scheduleRepo.save(Schedule.builder().dayOfWeek("wednesday").timeSlot("09:00-10:00").subject("Data Structures").room("Lab 101") .classId("101").teacher(teacher).build());
                scheduleRepo.save(Schedule.builder().dayOfWeek("thursday") .timeSlot("09:00-10:00").subject("Algorithms")      .room("Room 204").classId("101").teacher(teacher).build());
                scheduleRepo.save(Schedule.builder().dayOfWeek("friday")   .timeSlot("09:00-10:00").subject("Data Structures").room("Lab 101") .classId("101").teacher(teacher).build());
            });
        }
    }
}
