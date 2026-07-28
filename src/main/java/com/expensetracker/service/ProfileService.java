package com.expensetracker.service;

import com.expensetracker.dto.profileResponse.ChangePasswordRequest;
import com.expensetracker.dto.profileResponse.ProfileResponse;
import com.expensetracker.dto.profileResponse.UpdateProfileRequest;

public interface ProfileService {

    ProfileResponse getProfile();

    ProfileResponse updateProfile(UpdateProfileRequest request);

    void changePassword(ChangePasswordRequest request);

}
