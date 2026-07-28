package com.expensetracker.repository;

import com.expensetracker.entity.Category;
import com.expensetracker.entity.User;
import com.expensetracker.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Get all categories of a particular type
    List<Category> findByTypeOrderByNameAsc(CategoryType type);

    // Find a specific category by ID and type
    Optional<Category> findByIdAndType(Long id, CategoryType type);

    // Find category by name and type
    Optional<Category> findByNameAndType(String name, CategoryType type);

    // Prevent duplicate categories
    boolean existsByNameAndType(String name, CategoryType type);

}
