package com.expensetracker.controller;

import com.expensetracker.dto.otpRequest.ForgotPasswordRequest;
import com.expensetracker.dto.otpRequest.ResetPasswordRequest;
import com.expensetracker.dto.request.LoginRequest;
import com.expensetracker.dto.request.RegisterRequest;
import com.expensetracker.dto.response.ApiResponse;
import com.expensetracker.dto.response.LoginResponse;
import com.expensetracker.service.AuthService;
import com.expensetracker.service.OtpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "Authentication",
        description = "APIs for user authentication, registration, login and password recovery."
)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final OtpService otpService;

    @PostMapping("/register")
    @Operation(
            summary = "Register User",
            description = "Registers a new user and sends a welcome email."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User registered successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request or email already exists")
    })
    public ResponseEntity<ApiResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) throws BadRequestException {

        ApiResponse response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login User",
            description = "Authenticates the user and returns a JWT token."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login successful"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Invalid email or password")
    })
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    @Operation(
            summary = "Forgot Password",
            description = "Generates an OTP and sends it to the registered email."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OTP sent successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<ApiResponse> forgetPassword(
            @Valid @RequestBody ForgotPasswordRequest request
    ) {

        otpService.forgotPassword(request);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("OTP sent via email.")
                        .build()
        );
    }

    @PostMapping("/reset-password")
    @Operation(
            summary = "Reset Password",
            description = "Resets the user's password after successful OTP verification."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Password reset successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid or expired OTP")
    })
    public ResponseEntity<ApiResponse> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
    ) {

        otpService.resetPassword(request);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Password reset successfully.")
                        .build()
        );
    }

}
