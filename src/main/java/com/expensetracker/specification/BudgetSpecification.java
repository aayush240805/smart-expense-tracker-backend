package com.expensetracker.specification;

import com.expensetracker.entity.Budget;
import com.expensetracker.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BudgetSpecification {

    public static Specification<Budget> budgetSpecification(
            User user,
            Long categoryId,
            Integer month,
            Integer year
    ) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Logged-in user's budgets only
            predicates.add(
                    criteriaBuilder.equal(
                            root.get("user"),
                            user
                    )
            );

            if (categoryId != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("category").get("id"),
                                categoryId
                        )
                );
            }

            if (month != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("month"),
                                month
                        )
                );
            }

            if (year != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("year"),
                                year
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
