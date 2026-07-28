package com.expensetracker.controller;

import com.expensetracker.dto.request.BudgetRequest;
import com.expensetracker.dto.response.ApiResponse;
import com.expensetracker.dto.response.BudgetResponse;
import com.expensetracker.dto.response.PageResponse;
import com.expensetracker.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budgets")
@Tag(
        name = "Budget",
        description = "APIs for managing monthly budgets."
)
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    @Operation(
            summary = "Create Budget",
            description = "Creates a monthly budget for a category."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Budget created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Budget already exists")
    })
    public ResponseEntity<BudgetResponse> addBudget(
            @Valid @RequestBody BudgetRequest request
    ) {

        BudgetResponse response = budgetService.addBudget(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(
            summary = "Get All Budgets",
            description = "Returns all budgets of the logged-in user."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Budgets retrieved successfully")
    })
    public ResponseEntity<PageResponse<BudgetResponse>> getAllBudgets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "month") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year
    ) {

        PageResponse<BudgetResponse> responses = budgetService.getAllBudgets(
                page,
                size,
                sortBy,
                sortDir,
                categoryId,
                month,
                year
        );

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/id/{id}")
    @Operation(
            summary = "Get Budget By ID",
            description = "Returns a budget using its ID."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Budget retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Budget not found")
    })
    public ResponseEntity<BudgetResponse> getBudgetById(
            @PathVariable Long id
    ) {

        BudgetResponse response = budgetService.getBudgetById(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/id/{id}")
    @Operation(
            summary = "Update Budget",
            description = "Updates an existing monthly budget."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Budget updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Budget not found")
    })
    public ResponseEntity<BudgetResponse> updateBudget(
            @PathVariable Long id,
            @Valid @RequestBody BudgetRequest request
    ) {

        BudgetResponse response = budgetService.updateBudget(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/id/{id}")
    @Operation(
            summary = "Delete Budget",
            description = "Deletes a budget."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Budget deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Budget not found")
    })
    public ResponseEntity<ApiResponse> deleteBudget(
            @PathVariable Long id
    ) {

        budgetService.deleteBudget(id);

        return ResponseEntity.ok(new ApiResponse(true, "Budget deleted successfully."));
    }
}
