package com.expensetracker.dto.response;

import com.expensetracker.enums.CategoryType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentTransactionResponse {

    private Long id;

    private String title;

    private BigDecimal amount;

    private CategoryType transactionType; // INCOME or EXPENSE

    private String category;

    private LocalDateTime transactionDate;

}
