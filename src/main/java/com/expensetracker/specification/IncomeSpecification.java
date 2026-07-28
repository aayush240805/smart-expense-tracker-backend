package com.expensetracker.specification;

import com.expensetracker.entity.Income;
import com.expensetracker.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IncomeSpecification {

    public static Specification<Income> incomeSpecification(
            User user,
            String keyword,
            Long categoryId,
            String paymentMethod,
            LocalDate startDate,
            LocalDate endDate
    ) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Logged-in user's expenses only
            predicates.add(
                    criteriaBuilder.equal(
                            root.get("user"),
                            user
                    )
            );

            // Search by title or description
            if (keyword != null && !keyword.isBlank()) {

                // making case-insensitive
                Predicate titlePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + keyword.toLowerCase() + "%"
                );

                Predicate descriptionPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("description")),
                        "%" + keyword.toLowerCase() + "%"
                );

                predicates.add(
                        criteriaBuilder.or(
                                titlePredicate,
                                descriptionPredicate
                        )
                );

            }

            // Filter by category
            if (categoryId != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("category").get("id"),
                                categoryId
                        )
                );
            }

            // Filter by payment method
            if (paymentMethod != null && !paymentMethod.isBlank()) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("paymentMethod"),
                                paymentMethod
                        )
                );
            }

            // Filter by start date
            if (startDate != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("expenseDate"),
                                startDate
                        )
                );
            }

            // Filter by end date
            if (endDate != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("expenseDate"),
                                endDate
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
