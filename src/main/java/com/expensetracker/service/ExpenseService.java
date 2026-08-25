package com.expensetracker.service;

import com.expensetracker.dto.request.ExpenseRequest;
import com.expensetracker.dto.response.ExpenseResponse;
import com.expensetracker.dto.response.PageResponse;

import java.time.LocalDate;


public interface ExpenseService {

    ExpenseResponse addExpense(ExpenseRequest request);

    PageResponse<ExpenseResponse> getAllExpenses(
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

    ExpenseResponse getExpenseById(Long expenseId);

    ExpenseResponse updateExpense(Long expenseId, ExpenseRequest request);

    void deleteExpense(Long expenseId);

}
