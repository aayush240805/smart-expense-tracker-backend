package com.expensetracker.service.impl;

import com.expensetracker.dto.reportResponse.CategoryExpenseResponse;
import com.expensetracker.dto.response.DashboardResponse;
import com.expensetracker.dto.response.RecentTransactionResponse;
import com.expensetracker.entity.Expense;
import com.expensetracker.entity.Income;
import com.expensetracker.entity.User;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.IncomeRepository;
import com.expensetracker.service.DashboardService;
import com.expensetracker.util.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final IncomeRepository incomeRepository;

    private final ExpenseRepository expenseRepository;

    private final BudgetRepository budgetRepository;

    private final CurrentUserService currentUserService;


    @Override
    @Transactional(readOnly = true)
    public DashboardResponse getDashboard(Integer month, Integer year) {

        User currentUser = currentUserService.getCurrentUser();

        // Monthly Expense
        BigDecimal totalExpense = expenseRepository.getMonthlyExpense(currentUser, month, year);

        // Monthly Income
        BigDecimal totalIncome = incomeRepository.getMonthlyIncome(currentUser, month, year);

        // Current Balance -> NOT MONTHLY
        BigDecimal currentBalance = incomeRepository.getTotalIncome(currentUser).subtract(expenseRepository.getTotalExpense(currentUser));

        BigDecimal totalBudget = budgetRepository.getMonthlyBudget(currentUser, month, year);

        BigDecimal remainingBudget = totalBudget.subtract(totalExpense);

        List<Expense> recent5ExpenseTransactions = expenseRepository.findTop5ByUserOrderByExpenseDateDesc(currentUser);
        List<Income> recent5IncomeTransactions = incomeRepository.findTop5ByUserOrderByIncomeDateDesc(currentUser);

        List<RecentTransactionResponse> expenseTransactions = recent5ExpenseTransactions.stream()
                .map(expense -> RecentTransactionResponse.builder()
                        .id(expense.getId())
                        .title(expense.getTitle())
                        .amount(expense.getAmount())
                        .transactionType(expense.getCategory().getType())
                        .category(expense.getCategory().getName())
                        .transactionDate(expense.getExpenseDate())
                        .build())
                .toList();

        List<RecentTransactionResponse> incomeTransactions = recent5IncomeTransactions.stream()
                .map(income -> RecentTransactionResponse.builder()
                        .id(income.getId())
                        .title(income.getTitle())
                        .amount(income.getAmount())
                        .transactionType(income.getCategory().getType())
                        .category(income.getCategory().getName())
                        .transactionDate(income.getIncomeDate())
                        .build())
                .toList();

        List<RecentTransactionResponse> recentTransactions =
                Stream.concat(expenseTransactions.stream(), incomeTransactions.stream())
                    .sorted(Comparator.comparing(RecentTransactionResponse::getTransactionDate).reversed())
                        .limit(5)
                        .toList();

        List<CategoryExpenseResponse> categoryExpense = expenseRepository.getCategoryWiseExpense(currentUser, month, year);

        return DashboardResponse.builder()
                .totalExpense(totalExpense)
                .totalIncome(totalIncome)
                .currentBalance(currentBalance)
                .totalBudget(totalBudget)
                .remainingBudget(remainingBudget)
                .recentTransactions(recentTransactions)
                .categoryExpenses(categoryExpense)
                .build();
    }

}
