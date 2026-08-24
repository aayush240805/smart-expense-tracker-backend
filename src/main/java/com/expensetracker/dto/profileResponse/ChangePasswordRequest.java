package com.expensetracker.dto.profileResponse;

import com.expensetracker.validation.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {

    @NotBlank(
            message = "Current password is required.",
            groups = ValidationGroups.Required.class
    )
    private String currentPassword;

    @NotBlank(
            message = "Enter your new password.",
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
    private String newPassword;

    @NotBlank(
            message = "Confirm your password.",
            groups = ValidationGroups.Required.class
    )
    private String confirmedPassword;

}
