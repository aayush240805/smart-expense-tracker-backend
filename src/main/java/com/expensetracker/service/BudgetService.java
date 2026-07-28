package com.expensetracker.service;

import com.expensetracker.dto.request.BudgetRequest;
import com.expensetracker.dto.response.BudgetResponse;
import com.expensetracker.dto.response.PageResponse;
import org.springframework.data.domain.Page;

public interface BudgetService {

    BudgetResponse addBudget(BudgetRequest request);

    PageResponse<BudgetResponse> getAllBudgets(
            int page,
            int size,
            String sortBy,
            String sortDir,
            Long categoryId,
            Integer month,
            Integer year
    );

    BudgetResponse getBudgetById(Long budgetId);

    BudgetResponse updateBudget(Long budgetId, BudgetRequest request);

    void deleteBudget(Long budgetId);
}
