package com.expensetracker.dto.request;

import com.expensetracker.enums.PaymentMethod;
import com.expensetracker.validation.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeRequest {

    @NotBlank(
            message = "Title is required.",
            groups = ValidationGroups.Required.class
    )
    private String title;

    @NotNull(
            message = "Amount is required.",
            groups = ValidationGroups.Required.class
    )
    @Positive(
            message = "Amount must be greater than zero.",
            groups = ValidationGroups.Range.class
    )
    private BigDecimal amount;

    @NotNull(
            message = "Category is required.",
            groups = ValidationGroups.Required.class
    )
    private Long categoryId;

    private String description;

    @NotNull(
            message = "Income Date is required.",
            groups = ValidationGroups.Required.class
    )
    private LocalDate incomeDate;

    @NotNull(
            message = "Payment method is required.",
            groups = ValidationGroups.Required.class
    )
    private PaymentMethod paymentMethod;
}
