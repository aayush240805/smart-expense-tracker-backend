package com.expensetracker.dto.request;

import com.expensetracker.validation.ValidationGroups;
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

    @NotNull(
            message = "Monthly limit is required",
            groups = ValidationGroups.Required.class
    )
    private BigDecimal monthlyLimit;

    @NotNull(
            message = "Category is required",
            groups = ValidationGroups.Required.class
    )
    private Long categoryId;

    @NotNull(
            message = "Month is required",
            groups = ValidationGroups.Required.class
    )
    @Min(1)
    @Max(12)
    private Integer month;

    @NotNull(
            message = "Year is required",
            groups = ValidationGroups.Required.class
    )
    @Min(2025)
    private Integer year;
}
