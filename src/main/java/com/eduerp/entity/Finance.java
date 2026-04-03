package com.eduerp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "finance")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Finance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    private Double amount;
    private Double paidAmount;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private FeeStatus feeStatus = FeeStatus.UNPAID;

    private String semester;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum FeeStatus {
        PAID, UNPAID, PARTIAL
    }
}
