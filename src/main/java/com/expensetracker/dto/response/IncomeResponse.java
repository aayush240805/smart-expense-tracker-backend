package com.expensetracker.dto.response;

import com.expensetracker.enums.PaymentMethod;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeResponse {

    private Long id;

    private String title;

    private BigDecimal amount;

    private String category;

    private String description;

    private LocalDate incomeDate;

    private PaymentMethod paymentMethod;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
