package com.expensetracker.controller;

import com.expensetracker.dto.profileResponse.ChangePasswordRequest;
import com.expensetracker.dto.profileResponse.ProfileResponse;
import com.expensetracker.dto.profileResponse.UpdateProfileRequest;
import com.expensetracker.dto.response.ApiResponse;
import com.expensetracker.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@Tag(
        name = "Profile",
        description = "User profile management."
)
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    @Operation(
            summary = "Get Profile",
            description = "Returns the logged-in user's profile."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Profile retrieved successfully")
    })
    public ResponseEntity<ProfileResponse> getProfile() {

        ProfileResponse response = profileService.getProfile();

        return ResponseEntity.ok(response);

    }

    @PutMapping("/update-profile")
    @Operation(
            summary = "Update Profile",
            description = "Updates the logged-in user's profile."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid profile details")
    })
    public ResponseEntity<ProfileResponse> updateProfile(
            @RequestBody UpdateProfileRequest request
    ) {

        ProfileResponse response = profileService.updateProfile(request);

        return ResponseEntity.ok(response);

    }

    @PutMapping("/change-password")
    @Operation(
            summary = "Change Password",
            description = "Change password of the logged-in user's profile."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OTP sent successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<ApiResponse> changePassword(
            @RequestBody ChangePasswordRequest request
    ) {

        profileService.changePassword(request);

        return ResponseEntity.ok(new ApiResponse(true, "Password changed successfully."));

    }

}
