package com.expensetracker.service.impl;

import com.expensetracker.dto.request.IncomeRequest;
import com.expensetracker.dto.response.IncomeResponse;
import com.expensetracker.dto.response.PageResponse;
import com.expensetracker.entity.Category;
import com.expensetracker.entity.Income;
import com.expensetracker.entity.User;
import com.expensetracker.exception.ResourceNotFoundException;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.IncomeRepository;
import com.expensetracker.service.IncomeService;
import com.expensetracker.specification.IncomeSpecification;
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

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;

    private final CategoryRepository categoryRepository;

    private final CurrentUserService currentUserService;

    // Helper method (IncomeResponse builder)
    private IncomeResponse mapToIncomeResponse(Income income) {
        return IncomeResponse.builder()
                .id(income.getId())
                .title(income.getTitle())
                .amount(income.getAmount())
                .category(income.getCategory().getName())
                .description(income.getDescription())
                .incomeDate(income.getIncomeDate())
                .paymentMethod(income.getPaymentMethod())
                .createdAt(income.getCreatedAt())
                .updatedAt(income.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public IncomeResponse addIncome(IncomeRequest request) {
        User currentUser = currentUserService.getCurrentUser();

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found."));

        Income income = new Income();
        income.setTitle(request.getTitle());
        income.setAmount(request.getAmount());
        income.setDescription(request.getDescription());
        income.setIncomeDate(request.getIncomeDate());
        income.setPaymentMethod(request.getPaymentMethod());

        // Foreign keys
        income.setCategory(category);
        income.setUser(currentUser);

        // To get createdAt and updatedAt fields
        Income savedIncome =  incomeRepository.save(income);

        return mapToIncomeResponse(savedIncome);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<IncomeResponse> getAllIncomes(
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

        Specification<Income> specification =
                IncomeSpecification.incomeSpecification(
                        currentUser,
                        keyword,
                        categoryId,
                        paymentMethod,
                        startDate,
                        endDate
                );

        Page<Income> incomePage = incomeRepository.findAll(specification, pageable);

        Page<IncomeResponse> responsePage = incomePage.map(this::mapToIncomeResponse);

        return PaginationUtil.toPageResponse(responsePage);
    }

    @Override
    @Transactional(readOnly = true)
    public IncomeResponse getIncomeById(Long incomeId) {

        User currentUser = currentUserService.getCurrentUser();

        Income income = incomeRepository.findByIdAndUser(incomeId, currentUser).orElseThrow(() -> new ResourceNotFoundException("Income not found."));

        return mapToIncomeResponse(income);
    }

    @Override
    @Transactional
    public IncomeResponse updateIncome(Long incomeId, IncomeRequest request) {
        User currentUser = currentUserService.getCurrentUser();

        // To prevent access income of a user by another user
        Income income = incomeRepository.findByIdAndUser(incomeId, currentUser).orElseThrow(() -> new ResourceNotFoundException("Income not found."));

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found."));

        income.setTitle(request.getTitle());
        income.setAmount(request.getAmount());
        income.setDescription(request.getDescription());
        income.setIncomeDate(request.getIncomeDate());
        income.setPaymentMethod(request.getPaymentMethod());
        income.setPaymentMethod(request.getPaymentMethod());
        income.setUpdatedAt(LocalDateTime.now(Clock.systemDefaultZone()));
        // Foreign key
        income.setCategory(category);

        // To get createdAt and updatedAt fields
        Income updatedIncome = incomeRepository.save(income);

        return mapToIncomeResponse(updatedIncome);
    }

    @Override
    @Transactional
    public void deleteIncome(Long incomeId) {

        User currentUser = currentUserService.getCurrentUser();

        Income income = incomeRepository.findByIdAndUser(incomeId, currentUser).orElseThrow(() -> new ResourceNotFoundException("Income not found."));

        incomeRepository.delete(income);

    }
}
