package com.expensetracker.repository;

import com.expensetracker.entity.Expense;
import com.expensetracker.entity.Income;
import com.expensetracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long>, JpaSpecificationExecutor<Income> {

    // User's all incomes
    Page<Income> findAll(Specification specification, Pageable pageable);

    // Find income by id and user
    Optional<Income> findByIdAndUser(Long id, User user);

    // Latest incomes
    List<Income> findByUserOrderByIncomeDateDesc(User user);

    // Latest 5 incomes (used for dashboard view)
    List<Income> findTop5ByUserOrderByIncomeDateDesc(User user);

    // All Latest incomes
    List<Income> findAllByUserOrderByIncomeDateDesc(User user);

    // Incomes of specific time period
    List<Income> findByUserAndIncomeDateBetween(User user, LocalDate startDate, LocalDate endDate);

    // User's all incomes of specific category
    List<Income> findByUserAndCategoryId(User user, Long categoryId);

    @Query("""
            SELECT COALESCE(SUM(i.amount), 0)
            FROM Income i
            WHERE i.user = :user
            """)
    BigDecimal getTotalIncome(User user);

    Long countByUser(User user);

    @Query("""
            SELECT COALESCE(SUM(i.amount), 0)
            FROM Income i
            WHERE i.user = :user
            AND MONTH(i.incomeDate) = :month
            AND YEAR(i.incomeDate) = :year
""")
    BigDecimal getMonthlyIncome(User user, Integer month, Integer year);

}
