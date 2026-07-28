package com.expensetracker.controller;

import com.expensetracker.dto.response.DashboardResponse;
import com.expensetracker.service.DashboardService;
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


@RestController
@RequestMapping("/api/dashboard")
@Tag(
        name = "Dashboard",
        description = "Dashboard statistics and financial overview."
)
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @Operation(
            summary = "Get Dashboard",
            description = "Returns dashboard summary including total income, total expense, balance and recent transactions."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dashboard loaded successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<DashboardResponse> getDashboard(
            @RequestParam Integer month,
            @RequestParam Integer year
    ) {

        DashboardResponse response = dashboardService.getDashboard(month, year);

        return ResponseEntity.ok(response);
    }
}
