package com.expensetracker.controller;

import com.expensetracker.dto.request.IncomeRequest;
import com.expensetracker.dto.response.ApiResponse;
import com.expensetracker.dto.response.IncomeResponse;
import com.expensetracker.dto.response.PageResponse;
import com.expensetracker.service.IncomeService;
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
@RequestMapping("/api/incomes")
@Tag(
        name = "Income",
        description = "APIs for managing user income."
)
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @PostMapping
    @Operation(
            summary = "Add Income",
            description = "Creates a new income for the authenticated user."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Income created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid income details"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<IncomeResponse> addIncome(
            @Validated(ValidationSequence.class)
            @RequestBody IncomeRequest request
    ) {

        IncomeResponse response = incomeService.addIncome(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(
            summary = "Get All Incomes",
            description = "Returns all incomes of the logged-in user."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Incomes retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<PageResponse<IncomeResponse>> getAllIncomes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "incomeDate") String  sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String paymentMethod,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {

        PageResponse<IncomeResponse> response = incomeService.getAllIncomes(
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

    @GetMapping("/{incomeId}")
    @Operation(
            summary = "Get Income By ID",
            description = "Returns an expense using its ID."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Income retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Income not found")
    })
    public ResponseEntity<IncomeResponse> getIncomeById(
            @PathVariable Long incomeId
    ) {

        IncomeResponse response = incomeService.getIncomeById(incomeId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{incomeId}")
    @Operation(
            summary = "Update Income",
            description = "Updates an existing income."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Income updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Income not found")
    })
    public ResponseEntity<IncomeResponse> updateIncome(
            @PathVariable Long incomeId,

            @Validated(ValidationSequence.class)
            @RequestBody IncomeRequest request
    ) {

        IncomeResponse response = incomeService.updateIncome(incomeId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{incomeId}")
    @Operation(
            summary = "Delete Income",
            description = "Deletes an income."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Income deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "income not found")
    })
    public ResponseEntity<ApiResponse> deleteIncome(
            @PathVariable Long incomeId
    ) {

        incomeService.deleteIncome(incomeId);

        return ResponseEntity.ok(new ApiResponse(true, "Income deleted successfully."));
    }

}
