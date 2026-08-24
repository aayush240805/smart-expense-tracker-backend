package com.expensetracker.dto.request;

import com.expensetracker.validation.ValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

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
    private String password;

}
