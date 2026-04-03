package com.eduerp.repository;
import com.eduerp.entity.Finance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FinanceRepository extends JpaRepository<Finance, Long> {
    List<Finance> findByStudentId(Long studentId);
    Optional<Finance> findByStudentIdAndSemester(Long studentId, String semester);
    List<Finance> findByFeeStatus(Finance.FeeStatus status);
}
