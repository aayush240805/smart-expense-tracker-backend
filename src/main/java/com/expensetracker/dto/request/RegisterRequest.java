package com.expensetracker.dto.request;


import com.expensetracker.validation.ValidationGroups;
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
public class RegisterRequest {

    @NotBlank(
            message = "Full name is required",
            groups = ValidationGroups.Required.class
    )
    @Size(
            min = 3, max = 50,
            message = "Full name must be between 3 and 50 characters",
            groups = ValidationGroups.Size.class
    )
    private String fullName;

    @NotBlank(
            message = "Email is required",
            groups = ValidationGroups.Required.class
    )
    @Email(
            message = "Please enter a valid email address",
            groups = ValidationGroups.Format.class
    )
    private String email;

    @NotBlank(
            message = "Password is required",
            groups = ValidationGroups.Required.class
    )
    @Size(
            min = 6,
            message = "Password must be at least 6 characters.",
            groups = ValidationGroups.Size.class
    )
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
            message = "Password must contain uppercase, lowercase, number and special character",
            groups = ValidationGroups.Format.class
    )
    private String password;

}
