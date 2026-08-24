package com.expensetracker.dto.profileResponse;

import com.expensetracker.validation.ValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProfileRequest {

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

}
