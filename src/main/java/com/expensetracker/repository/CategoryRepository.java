package com.expensetracker.repository;

import com.expensetracker.entity.Category;
import com.expensetracker.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Get all categories of a particular type
    List<Category> findByTypeOrderByNameAsc(CategoryType type);

}
