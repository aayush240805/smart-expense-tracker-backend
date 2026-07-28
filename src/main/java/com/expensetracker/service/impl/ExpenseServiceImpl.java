package com.expensetracker.service.impl;

import com.expensetracker.dto.request.ExpenseRequest;
import com.expensetracker.dto.response.ExpenseResponse;
import com.expensetracker.dto.response.PageResponse;
import com.expensetracker.entity.Category;
import com.expensetracker.entity.Expense;
import com.expensetracker.entity.User;
import com.expensetracker.exception.ResourceNotFoundException;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.specification.ExpenseSpecification;
import com.expensetracker.util.CurrentUserService;
import com.expensetracker.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final CategoryRepository categoryRepository;

    private final CurrentUserService currentUserService;

    // Helper method (ExpenseResponse builder)
    private ExpenseResponse mapToExpenseResponse(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .amount(expense.getAmount())
                .category(expense.getCategory().getName())
                .description(expense.getDescription())
                .expenseDate(expense.getExpenseDate())
                .paymentMethod(expense.getPaymentMethod())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .build();
    }


    @Override
    @Transactional
    public ExpenseResponse addExpense(ExpenseRequest request) {

        User currentUser = currentUserService.getCurrentUser();

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found."));

        Expense expense = new Expense();
        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setPaymentMethod(request.getPaymentMethod());

        // Foreign keys
        expense.setCategory(category);
        expense.setUser(currentUser);

        // To get createdAt and updatedAt fields
        Expense savedExpense =  expenseRepository.save(expense);

        return mapToExpenseResponse(savedExpense);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ExpenseResponse> getAllExpenses(
            int page,
            int size,
            String sortBy,
            String sortDir,
            String keyword,
            Long categoryId,
            String paymentMethod,
            LocalDate startDate,
            LocalDate endDate
    ) {

        User currentUser = currentUserService.getCurrentUser();

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Expense> specification =
                ExpenseSpecification.expenseSpecification(
                        currentUser,
                        keyword,
                        categoryId,
                        paymentMethod,
                        startDate,
                        endDate
                );

        Page<Expense> expensePage = expenseRepository.findAll(specification, pageable);

        Page<ExpenseResponse> responsePage = expensePage.map(this::mapToExpenseResponse);

        return PaginationUtil.toPageResponse(responsePage);
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseResponse getExpenseById(Long expenseId) {

        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new ResourceNotFoundException("Expense not found."));

        return mapToExpenseResponse(expense);
    }

    @Override
    @Transactional
    public ExpenseResponse updateExpense(Long expenseId, ExpenseRequest request) {

        User currentUser = currentUserService.getCurrentUser();

        // To prevent access expense of a user by another user
        Expense expense = expenseRepository.findByIdAndUser(expenseId, currentUser);

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found."));

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setPaymentMethod(request.getPaymentMethod());
        expense.setPaymentMethod(request.getPaymentMethod());
        // Foreign key
        expense.setCategory(category);

        // To get createdAt and updatedAt fields
        Expense updatedExpense = expenseRepository.save(expense);

        return mapToExpenseResponse(updatedExpense);
    }

    @Override
    @Transactional
    public void deleteExpense(Long expenseId) {

        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new ResourceNotFoundException("Expense not found."));

        expenseRepository.delete(expense);

    }
}
