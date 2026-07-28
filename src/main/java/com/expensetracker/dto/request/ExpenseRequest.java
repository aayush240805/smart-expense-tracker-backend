package com.expensetracker.dto.request;

import com.expensetracker.enums.PaymentMethod;
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
public class ExpenseRequest {

    @NotBlank(message = "Title is required.")
    private String title;

    @NotNull(message = "Amount is required.")
    @Positive(message = "Amount must be greater than zero.")
    private BigDecimal amount;

    @NotNull(message = "Category is required.")
    private Long categoryId;

    private String description;

    @NotNull(message = "Expense Date is required.")
    private LocalDate expenseDate;

    @NotNull(message = "Payment method is required.")
    private PaymentMethod paymentMethod;
}
