package com.expensetracker.service.impl;

import com.expensetracker.dto.reportResponse.BudgetReportResponse;
import com.expensetracker.dto.reportResponse.CategoryExpenseResponse;
import com.expensetracker.dto.reportResponse.MonthlyReportResponse;
import com.expensetracker.entity.Budget;
import com.expensetracker.entity.User;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.IncomeRepository;
import com.expensetracker.service.ReportService;
import com.expensetracker.util.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final IncomeRepository incomeRepository;

    private final ExpenseRepository expenseRepository;

    private final BudgetRepository budgetRepository;

    private final CurrentUserService currentUserService;


    @Override
    @Transactional(readOnly = true)
    public MonthlyReportResponse getMonthlyExpenseReport(Integer month, Integer year) {

        User currentUser = currentUserService.getCurrentUser();

        BigDecimal monthlyIncome = incomeRepository.getMonthlyIncome(currentUser, month, year);
        BigDecimal monthlyExpense = expenseRepository.getMonthlyExpense(currentUser, month, year);
        BigDecimal monthlySaving = monthlyIncome.subtract(monthlyExpense);

        return MonthlyReportResponse.builder()
                .month(month)
                .year(year)
                .totalIncome(monthlyIncome)
                .totalExpense(monthlyExpense)
                .totalSaving(monthlySaving)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryExpenseResponse> getCategoryWiseExpenseReport(Integer month, Integer year) {

        User currentUser = currentUserService.getCurrentUser();

        return expenseRepository.getCategoryWiseExpense(currentUser, month, year);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryExpenseResponse> getCategoryWiseExpenseReport(User user, Integer month, Integer year) {

        return expenseRepository.getCategoryWiseExpense(user, month, year);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BudgetReportResponse> getBudgetReport(Integer month, Integer year) {

        User currentUser = currentUserService.getCurrentUser();

        List<Budget> thisMonthBudgets = budgetRepository.findByUserAndMonthAndYear(currentUser, month, year);

        List<BudgetReportResponse> budgetReportResponses = new ArrayList<>();

        for (Budget b : thisMonthBudgets) {
            BigDecimal monthlyLimit = b.getMonthlyLimit();
            BigDecimal spent = expenseRepository.getTotalSpentByCategoryAndMonth(currentUser, b.getCategory(), month, year);
            BigDecimal remaining = monthlyLimit.subtract(spent);

            Integer percentageUsed = 0;
            // To prevent ArithmeticException
            // Checking if the monthlyLimit is a +ve then it return -> 1 and then comparing it with zero
            if (monthlyLimit.compareTo(BigDecimal.ZERO) > 0) {

                percentageUsed = spent.multiply(BigDecimal.valueOf(100)).divide(monthlyLimit, 4, RoundingMode.HALF_UP).intValue();

            }


            budgetReportResponses.add(
                    BudgetReportResponse.builder()
                            .category(b.getCategory().getName())
                            .monthlyLimit(monthlyLimit)
                            .spent(spent)
                            .remaining(remaining)
                            .percentageUsed(percentageUsed)
                            .build()
            );
        }

        return budgetReportResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BudgetReportResponse> getBudgetReport(User user, Integer month, Integer year) {

        List<Budget> thisMonthBudgets = budgetRepository.findByUserAndMonthAndYear(user, month, year);

        List<BudgetReportResponse> budgetReportResponses = new ArrayList<>();

        for (Budget b : thisMonthBudgets) {
            BigDecimal monthlyLimit = b.getMonthlyLimit();
            BigDecimal spent = expenseRepository.getTotalSpentByCategoryAndMonth(user, b.getCategory(), month, year);
            BigDecimal remaining = monthlyLimit.subtract(spent);

            Integer percentageUsed = 0;
            // To prevent ArithmeticException
            // Checking if the monthlyLimit is a +ve then it returns -> 1 and then comparing it with zero
            if (monthlyLimit.compareTo(BigDecimal.ZERO) > 0) {

                percentageUsed = spent.multiply(BigDecimal.valueOf(100)).divide(monthlyLimit, 4, RoundingMode.HALF_UP).intValue();

            }


            budgetReportResponses.add(
                    BudgetReportResponse.builder()
                            .category(b.getCategory().getName())
                            .monthlyLimit(monthlyLimit)
                            .spent(spent)
                            .remaining(remaining)
                            .percentageUsed(percentageUsed)
                            .build()
            );
        }

        return budgetReportResponses;
    }
}
