package com.expensetracker.service;

import com.expensetracker.dto.response.CategoryResponse;
import com.expensetracker.enums.CategoryType;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getCategories(CategoryType type);

}
