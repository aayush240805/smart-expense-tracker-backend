package com.expensetracker.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetRequest {

    @NotNull(message = "Monthly limit is required")
    @Positive(message = "Monthly limit must be greater than zero")
    private BigDecimal monthlyLimit;

    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotNull(message = "Month is required")
    @Min(1)
    @Max(12)
    private Integer month;

    @NotNull(message = "Year is required")
    @Min(2025)
    private Integer year;
}
