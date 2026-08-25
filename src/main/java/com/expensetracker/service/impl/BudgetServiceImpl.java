package com.expensetracker.service.impl;

import com.expensetracker.dto.request.BudgetRequest;
import com.expensetracker.dto.response.BudgetResponse;
import com.expensetracker.dto.response.PageResponse;
import com.expensetracker.entity.Budget;
import com.expensetracker.entity.Category;
import com.expensetracker.entity.User;
import com.expensetracker.exception.DuplicateResourceException;
import com.expensetracker.exception.ResourceNotFoundException;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.BudgetService;
import com.expensetracker.specification.BudgetSpecification;
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
import org.springframework.web.client.ResourceAccessException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    private final CategoryRepository categoryRepository;

    private final ExpenseRepository expenseRepository;

    private final CurrentUserService currentUserService;


    // Helper method (BudgetResponse builder)
    private BudgetResponse mapToBudgetResponse(Budget budget, BigDecimal spent) {

        return BudgetResponse.builder()
                .id(budget.getId())
                .category(budget.getCategory().getName())
                .monthlyLimit(budget.getMonthlyLimit())
                .spent(spent)
                .remaining(budget.getMonthlyLimit().subtract(spent))
                .month(budget.getMonth())
                .year(budget.getYear())
                .createdAt(budget.getCreatedAt())
                .updatedAt(budget.getUpdatedAt())
                .build();
    }


    @Override
    @Transactional
    public BudgetResponse addBudget(BudgetRequest request) {

        User currentUser = currentUserService.getCurrentUser();

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceAccessException("Category not found."));

        Budget budget = new Budget();
        budget.setMonthlyLimit(request.getMonthlyLimit());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());
        // Foreign key
        budget.setCategory(category);
        budget.setUser(currentUser);

        budgetRepository.findByUserAndCategoryAndMonthAndYear(currentUser, category, request.getMonth(), request.getYear()).ifPresent( b -> {
            throw new DuplicateResourceException(
                    "Budget already exists for this category and month."
            );
        });

        Budget savedBudget = budgetRepository.save(budget);

        BigDecimal spent = expenseRepository.getTotalSpentByCategoryAndMonth(currentUser, category, budget.getMonth(), budget.getYear());

        return mapToBudgetResponse(savedBudget, spent);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<BudgetResponse> getAllBudgets(
            int page,
            int size,
            String sortBy,
            String sortDir,
            Long categoryId,
            Integer month,
            Integer year
    ) {

        User currentUser = currentUserService.getCurrentUser();

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Budget> specification =
                BudgetSpecification.budgetSpecification(
                        currentUser,
                        categoryId,
                        month,
                        year
                );

        Page<Budget> budgetPage = budgetRepository.findAll(specification, pageable);

        Page<BudgetResponse> responsePage = budgetPage.map(budget -> mapToBudgetResponse(budget, expenseRepository.getTotalSpentByCategoryAndMonth(currentUser, budget.getCategory(), budget.getMonth(), budget.getYear())));

        return PaginationUtil.toPageResponse(responsePage);
    }

    @Override
    @Transactional(readOnly = true)
    public BudgetResponse getBudgetById(Long budgetId) {

        User currentUser = currentUserService.getCurrentUser();
        Budget budget = budgetRepository.findByIdAndUser(budgetId, currentUser).orElseThrow(() -> new ResourceNotFoundException("Budget not found."));


        BigDecimal spent = expenseRepository.getTotalSpentByCategoryAndMonth(currentUser, budget.getCategory(), budget.getMonth(), budget.getYear());

        return mapToBudgetResponse(budget, spent);
    }

    @Override
    @Transactional
    public BudgetResponse updateBudget(Long budgetId, BudgetRequest request) {

        User currentUser = currentUserService.getCurrentUser();

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found."));

        Budget budget = budgetRepository.findByIdAndUser(budgetId, currentUser).orElseThrow(() -> new ResourceNotFoundException("Budget not found."));

        budget.setMonthlyLimit(request.getMonthlyLimit());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());
        budget.setUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));

        // Foreign key
        budget.setCategory(category);
        budget.setUser(currentUser);

        Optional<Budget> existingBudget = budgetRepository.findByUserAndCategoryAndMonthAndYear(currentUser, category, request.getMonth(), request.getYear());

        // If another budget exists with the same category, month, and year, then reject the update.
        if (existingBudget.isPresent() && !existingBudget.get().getId().equals(budgetId)) {
            throw  new DuplicateResourceException("Budget already exists for this category and month.");
        }

        Budget updatedBudget = budgetRepository.save(budget);

        BigDecimal spent = expenseRepository.getTotalSpentByCategoryAndMonth(currentUser, category, budget.getMonth(), budget.getYear());

        return mapToBudgetResponse(updatedBudget, spent);
    }

    @Override
    @Transactional
    public void deleteBudget(Long budgetId) {

        User currentUser = currentUserService.getCurrentUser();

        Budget budget = budgetRepository.findByIdAndUser(budgetId, currentUser).orElseThrow(() -> new ResourceNotFoundException("Budget not found."));

        budgetRepository.delete(budget);

    }
}
