package com.expensetracker.dto.reportResponse;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetReportResponse {

    private String category;

    private BigDecimal monthlyLimit;

    private BigDecimal spent;

    private BigDecimal remaining;

    private Integer percentageUsed;

}
