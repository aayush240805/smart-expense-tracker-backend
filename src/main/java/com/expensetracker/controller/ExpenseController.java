package com.expensetracker.controller;

import com.expensetracker.dto.request.ExpenseRequest;
import com.expensetracker.dto.response.ApiResponse;
import com.expensetracker.dto.response.ExpenseResponse;
import com.expensetracker.dto.response.PageResponse;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.validation.ValidationSequence;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/expenses")
@Tag(
        name = "Expense",
        description = "APIs for managing user expenses."
)
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    @Operation(
            summary = "Add Expense",
            description = "Creates a new expense for the authenticated user."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Expense created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid expense details"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<ExpenseResponse> addExpense(
            @Validated(ValidationSequence.class)
            @RequestBody ExpenseRequest request
    ) {

        ExpenseResponse response = expenseService.addExpense(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(
            summary = "Get All Expenses",
            description = "Returns all expenses of the logged-in user."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Expenses retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<PageResponse<ExpenseResponse>> getAllExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "expenseDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String paymentMethod,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {

        PageResponse<ExpenseResponse> response = expenseService.getAllExpenses(
                page,
                size,
                sortBy,
                sortDir,
                keyword,
                categoryId,
                paymentMethod,
                startDate,
                endDate
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{expenseId}")
    @Operation(
            summary = "Get Expense By ID",
            description = "Returns an expense using its ID."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Expense retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Expense not found")
    })
    public ResponseEntity<ExpenseResponse> getExpenseById(
            @PathVariable Long expenseId
    ) {

        ExpenseResponse response = expenseService.getExpenseById(expenseId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{expenseId}")
    @Operation(
            summary = "Update Expense",
            description = "Updates an existing expense."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Expense updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Expense not found")
    })
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable Long expenseId,
            @Validated(ValidationSequence.class)
            @RequestBody ExpenseRequest request
    ) {

        ExpenseResponse response = expenseService.updateExpense(expenseId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{expenseId}")
    @Operation(
            summary = "Delete Expense",
            description = "Deletes an expense."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Expense deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Expense not found")
    })
    public ResponseEntity<ApiResponse> deleteExpense(
            @PathVariable Long expenseId
    ) {

        expenseService.deleteExpense(expenseId);

        return ResponseEntity.ok(new ApiResponse(true, "Expense deleted successfully."));
    }

}
