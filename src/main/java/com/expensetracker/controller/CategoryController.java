package com.expensetracker.controller;

import com.expensetracker.dto.response.CategoryResponse;
import com.expensetracker.enums.CategoryType;
import com.expensetracker.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(@RequestParam CategoryType type) {

        List<CategoryResponse> responses = categoryService.getCategories(type);

        return ResponseEntity.ok(responses);

    }

}
