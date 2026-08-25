package com.expensetracker.repository;

import com.expensetracker.entity.Budget;
import com.expensetracker.entity.Category;
import com.expensetracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long>, JpaSpecificationExecutor<Budget> {

    // User's all budgets
    Page<Budget> findAll(Specification specification, Pageable pageable);

    // Find budget by id and user
    Optional<Budget> findByIdAndUser(Long id, User user);

    // Find Budget by User + Category + Month + Year
    Optional<Budget> findByUserAndCategoryAndMonthAndYear(User user, Category category, Integer month, Integer year);

    List<Budget> findByUserAndMonthAndYear(User user, Integer month, Integer year);

    @Query("""
            SELECT COALESCE(SUM(b.monthlyLimit), 0)
            FROM Budget b
            WHERE b.user = :user
            AND b.month = :month
            AND b.year = :year
            """)
    BigDecimal getMonthlyBudget(User user, Integer month, Integer year);
}
