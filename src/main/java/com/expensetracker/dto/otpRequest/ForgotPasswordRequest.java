package com.expensetracker.dto.otpRequest;

import com.expensetracker.validation.ValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForgotPasswordRequest {

    @NotBlank(
            message = "Email is required",
            groups = ValidationGroups.Required.class
    )
    @Email(
            message = "Please enter a valid email address.",
            groups = ValidationGroups.Format.class
    )
    private String email;

}
