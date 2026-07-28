package com.expensetracker.service;

import com.expensetracker.dto.reportResponse.BudgetReportResponse;
import com.expensetracker.dto.reportResponse.CategoryExpenseResponse;
import com.expensetracker.dto.reportResponse.MonthlyReportResponse;
import com.expensetracker.entity.User;

import java.util.List;

public interface ReportService {

    MonthlyReportResponse getMonthlyExpenseReport(Integer month, Integer year);

    List<CategoryExpenseResponse> getCategoryWiseExpenseReport(Integer month, Integer year);
    List<CategoryExpenseResponse> getCategoryWiseExpenseReport(User user, Integer month, Integer year);

    List<BudgetReportResponse> getBudgetReport(Integer month, Integer year);
    List<BudgetReportResponse> getBudgetReport(User user, Integer month, Integer year);
}
