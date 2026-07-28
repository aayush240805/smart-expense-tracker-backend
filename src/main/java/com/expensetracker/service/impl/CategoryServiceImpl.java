package com.expensetracker.service.impl;

import com.expensetracker.dto.response.CategoryResponse;
import com.expensetracker.entity.Category;
import com.expensetracker.enums.CategoryType;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getCategories(CategoryType type) {


        List<Category> categories = categoryRepository.findByTypeOrderByNameAsc(type);

        return categories.stream()
                .map(category ->
                        CategoryResponse.builder()
                                .id(category.getId())
                                .name(category.getName())
                                .build())
                .toList();

    }

}
