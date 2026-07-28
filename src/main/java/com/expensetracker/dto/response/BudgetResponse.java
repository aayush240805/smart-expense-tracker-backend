package com.expensetracker.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetResponse {

    private Long id;

    private String category;

    private BigDecimal monthlyLimit;

    private BigDecimal spent;

    private BigDecimal remaining;

    private Integer month;

    private Integer year;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
