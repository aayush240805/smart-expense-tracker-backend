package com.expensetracker.service;

import com.expensetracker.dto.request.IncomeRequest;
import com.expensetracker.dto.response.IncomeResponse;
import com.expensetracker.dto.response.PageResponse;

import java.time.LocalDate;


public interface IncomeService {

    IncomeResponse addIncome(IncomeRequest request);

    PageResponse<IncomeResponse> getAllIncomes(
            int page,
            int size,
            String sortBy,
            String sortDir,
            String keyword,
            Long categoryId,
            String paymentMethod,
            LocalDate startDate,
            LocalDate endDate
    );

    IncomeResponse getIncomeById(Long incomeId);

    IncomeResponse updateIncome(Long incomeId, IncomeRequest request);

    void deleteIncome(Long incomeId);
}
