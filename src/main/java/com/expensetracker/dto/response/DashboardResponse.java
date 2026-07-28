package com.expensetracker.dto.response;

import com.expensetracker.dto.reportResponse.CategoryExpenseResponse;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private BigDecimal totalExpense;

    private BigDecimal totalIncome;

    private BigDecimal currentBalance;

    private BigDecimal totalBudget;

    private BigDecimal remainingBudget;

    private List<RecentTransactionResponse> recentTransactions;

    private List<CategoryExpenseResponse> categoryExpenses;
}
