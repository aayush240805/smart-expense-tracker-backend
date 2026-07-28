package com.expensetracker.dto.otpRequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordRequest {

    @Email(message = "Invalid email.")
    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "OTP is required.")
    private String otp;

    @NotBlank(message = "New password is required.")
    @Size(min = 6, message = "Password must be at least 6 characters.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
            message = "Password must contain uppercase, lowercase, number and special character"
    )
    private String newPassword;

    @NotBlank(message = "Confirm your password.")
    private String confirmedPassword;

}
