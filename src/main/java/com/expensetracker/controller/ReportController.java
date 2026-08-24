package com.expensetracker.controller;

import com.expensetracker.dto.reportResponse.BudgetReportResponse;
import com.expensetracker.dto.reportResponse.CategoryExpenseResponse;
import com.expensetracker.dto.reportResponse.MonthlyReportResponse;
import com.expensetracker.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@Tag(
        name = "Reports",
        description = "Financial reports and analytics."
)
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // GET /api/reports/monthly?month=7&year=2026
    @GetMapping("/monthly")
    @Operation(
            summary = "Monthly Summary",
            description = "Returns monthly income, expense and balance."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Report generated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<MonthlyReportResponse> getMonthlyExpenseReport(
            @RequestParam Integer month,
            @RequestParam Integer year
    ) {

        MonthlyReportResponse response = reportService.getMonthlyExpenseReport(month, year);

        return ResponseEntity.ok(response);
    }

    // GET /api/reports/category-wise-expense?month=7&year=2026
    @GetMapping("/category-wise-expense")
    @Operation(
            summary = "Category-wise Expense Report",
            description = "Returns total expenses grouped by category."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Report generated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<CategoryExpenseResponse>> getCategoryWiseExpenseReport(
            @RequestParam Integer month,
            @RequestParam Integer year
    ) {

        List<CategoryExpenseResponse> responses = reportService.getCategoryWiseExpenseReport(month, year);

        return ResponseEntity.ok(responses);
    }

    // GET /api/reports/budget?month=7&year=2026
    @GetMapping("/budget")
    @Operation(
            summary = "Budget Report",
            description = "Returns budget, spent amount and remaining balance for every category."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Report generated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<BudgetReportResponse>> getBudgetReport(
            @RequestParam Integer month,
            @RequestParam Integer year
    ) {

        List<BudgetReportResponse> response = reportService.getBudgetReport(month, year);

        return ResponseEntity.ok(response);
    }

}
