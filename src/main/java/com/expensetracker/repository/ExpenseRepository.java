package com.expensetracker.repository;

import com.expensetracker.dto.reportResponse.CategoryExpenseResponse;
import com.expensetracker.entity.Category;
import com.expensetracker.entity.Expense;
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
public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {

    // User's all expenses
    Page<Expense> findAll(Specification specification, Pageable pageable);

    // Find expense by id and user
    Optional<Expense> findByIdAndUser(Long id, User user);

    // Latest 5 expenses (used for dashboard view)
    List<Expense> findTop5ByUserOrderByExpenseDateDesc(User user);

    // All Latest expenses
    List<Expense> findAllByUserOrderByExpenseDateDesc(User user);

    @Query("""
            SELECT COALESCE(SUM(e.amount), 0)
            FROM Expense e
            WHERE e.user = :user
            """)
    BigDecimal getTotalExpense(User user);

    @Query("""
            SELECT COALESCE(sum(e.amount), 0)
            FROM Expense e
            WHERE e.user = :user
            AND e.category = :category
            AND MONTH(e.expenseDate) = :month
            AND YEAR(e.expenseDate) = :year
            """)
    BigDecimal getTotalSpentByCategoryAndMonth(User user, Category category, Integer month, Integer year);

    @Query("""
            SELECT COALESCE(SUM(e.amount), 0)
            FROM Expense e
            WHERE e.user = :user
            AND MONTH(e.expenseDate) = :month
            AND YEAR(e.expenseDate) = :year
            """)
    BigDecimal getMonthlyExpense(User user, Integer month, Integer year);

    // JPQL DTO Projection: Instead of returning Object[], we'll return DTOs directly.
    @Query("""
            SELECT new com.expensetracker.dto.reportResponse.CategoryExpenseResponse(
                e.category.name,
                COALESCE(sum(e.amount), 0)
            )
            FROM Expense e
            WHERE e.user = :user
            AND MONTH(e.expenseDate) = :month
            AND YEAR(e.expenseDate) = :year
            GROUP BY e.category.name
            ORDER BY SUM(e.amount) DESC
            """)
    List<CategoryExpenseResponse> getCategoryWiseExpense(User user, Integer month, Integer year);



}
