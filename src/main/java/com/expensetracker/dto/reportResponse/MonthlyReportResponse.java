package com.expensetracker.dto.reportResponse;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyReportResponse {

    private Integer month;

    private Integer year;

    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    private BigDecimal totalSaving;
}
