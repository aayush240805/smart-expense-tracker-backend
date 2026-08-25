package com.expensetracker.config;

import com.expensetracker.entity.Category;
import com.expensetracker.enums.CategoryType;
import com.expensetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {

        if (categoryRepository.findByTypeOrderByNameAsc(CategoryType.EXPENSE).isEmpty()) {
            saveExpenseCategories();
        }

        if (categoryRepository.findByTypeOrderByNameAsc(CategoryType.INCOME).isEmpty()) {
            saveIncomeCategories();
        }

        log.info("Default categories inserted successfully.");

    }

    private void saveExpenseCategories() {

        saveCategory("Food", CategoryType.EXPENSE);
        saveCategory("Travel", CategoryType.EXPENSE);
        saveCategory("Shopping", CategoryType.EXPENSE);
        saveCategory("Bills", CategoryType.EXPENSE);
        saveCategory("Health", CategoryType.EXPENSE);
        saveCategory("Education", CategoryType.EXPENSE);
        saveCategory("Entertainment", CategoryType.EXPENSE);
        saveCategory("Transportation", CategoryType.EXPENSE);
        saveCategory("Rent", CategoryType.EXPENSE);
        saveCategory("Other", CategoryType.EXPENSE);

    }

    private void saveIncomeCategories() {

        saveCategory("Salary", CategoryType.INCOME);
        saveCategory("Freelancing", CategoryType.INCOME);
        saveCategory("Business", CategoryType.INCOME);
        saveCategory("Investment", CategoryType.INCOME);
        saveCategory("Gift", CategoryType.INCOME);
        saveCategory("Bonus", CategoryType.INCOME);
        saveCategory("Other", CategoryType.INCOME);

    }

    private void saveCategory(String name, CategoryType type) {

        Category category = Category.builder()
                .name(name)
                .type(type)
                .build();

        categoryRepository.save(category);
    }
}