package com.expensetracker.entity;

import com.expensetracker.enums.CategoryType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories",
        uniqueConstraints = {
            @UniqueConstraint(
                name = "uk_category_name_type",
                    columnNames = {"name", "type"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;

    @OneToMany(
            mappedBy = "category"
    )
    private List<Expense> expenses;

    @OneToMany(
            mappedBy = "category"
    )
    private List<Income> incomes;

    @OneToMany(
            mappedBy = "category"
    )
    private List<Budget> budgets;

}
