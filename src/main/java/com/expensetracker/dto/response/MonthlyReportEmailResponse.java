package com.expensetracker.dto.response;

import com.expensetracker.dto.reportResponse.BudgetReportResponse;
import com.expensetracker.dto.reportResponse.CategoryExpenseResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyReportEmailResponse {

    private Integer month;

    private Integer year;

    private BigDecimal totalExpense;

    private BigDecimal totalIncome;

    private BigDecimal totalSavings;

    private List<CategoryExpenseResponse> categoryWiseExpenses;

    private List<BudgetReportResponse> budgetReports;

}
