package com.expensetracker.service.impl;

import com.expensetracker.dto.reportResponse.BudgetReportResponse;
import com.expensetracker.dto.reportResponse.CategoryExpenseResponse;
import com.expensetracker.dto.response.MonthlyReportEmailResponse;
import com.expensetracker.entity.User;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.IncomeRepository;
import com.expensetracker.repository.UserRepository;
import com.expensetracker.service.EmailService;
import com.expensetracker.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonthlyReportEmailService implements com.expensetracker.service.MonthlyReportEmailService {

    private final UserRepository userRepository;

    private final ExpenseRepository expenseRepository;

    private final IncomeRepository incomeRepository;
    
    private final ReportService reportService;

    private final EmailService emailService;

    @Override
    @Transactional
    public void sendMonthlyReportEmail() {

        YearMonth previousMonth = YearMonth.now().minusMonths(1);

        int month = previousMonth.getMonthValue();

        int year = previousMonth.getYear();


        List<User> users = userRepository.findAll();

        for (User user : users) {

            BigDecimal monthlyExpense = expenseRepository.getMonthlyExpense(user, month, year);

            BigDecimal monthlyIncome = incomeRepository.getMonthlyIncome(user, month, year);

            if (monthlyExpense == null) {
                monthlyExpense = BigDecimal.ZERO;
            }

            if (monthlyIncome == null) {
                monthlyIncome = BigDecimal.ZERO;
            }

            BigDecimal savings = monthlyIncome.subtract(monthlyExpense);

            List<CategoryExpenseResponse> categoryWiseExpenses = reportService.getCategoryWiseExpenseReport(user, month, year);

            List<BudgetReportResponse> budgets = reportService.getBudgetReport(user, month, year);

            MonthlyReportEmailResponse report = MonthlyReportEmailResponse.builder().
                    month(month)
                    .year(year)
                    .totalExpense(monthlyExpense)
                    .totalIncome(monthlyIncome)
                    .totalSavings(savings)
                    .categoryWiseExpenses(categoryWiseExpenses)
                    .budgetReports(budgets)
                    .build();

            emailService.sendMonthlyReportEmail(user, report);

        }

    }

}
