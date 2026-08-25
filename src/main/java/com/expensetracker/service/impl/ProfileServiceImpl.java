package com.expensetracker.service.impl;

import com.expensetracker.dto.profileResponse.ChangePasswordRequest;
import com.expensetracker.dto.profileResponse.ProfileResponse;
import com.expensetracker.dto.profileResponse.UpdateProfileRequest;
import com.expensetracker.entity.User;
import com.expensetracker.repository.UserRepository;
import com.expensetracker.service.EmailService;
import com.expensetracker.service.ProfileService;
import com.expensetracker.util.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final CurrentUserService currentUserService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;


    @Override
    @Transactional(readOnly = true)
    public ProfileResponse getProfile() {

        User currentUser = currentUserService.getCurrentUser();

        return ProfileResponse.builder()
                .id(currentUser.getId())
                .fullName(currentUser.getFullName())
                .email(currentUser.getEmail())
                .profilePicture(currentUser.getProfilePicture())
                .role(currentUser.getRole())
                .createdAt(currentUser.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public ProfileResponse updateProfile(UpdateProfileRequest request) {

        User currentUser = currentUserService.getCurrentUser();

        currentUser.setFullName(request.getFullName());

        User updatedProfile = userRepository.save(currentUser);

        return ProfileResponse.builder()
                .id(updatedProfile.getId())
                .fullName(updatedProfile.getFullName())
                .email(updatedProfile.getEmail())
                .role(updatedProfile.getRole())
                .createdAt(updatedProfile.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {

        User currentUser = currentUserService.getCurrentUser();

        if (!passwordEncoder.matches(request.getCurrentPassword(), currentUser.getPassword())) {

            throw new IllegalArgumentException("Current password is incorrect.");

        }

        if (!request.getNewPassword().equals(request.getConfirmedPassword())) {

            throw new IllegalArgumentException("New password and confirm password do not match.");

        }

        currentUser.setPassword(passwordEncoder.encode(request.getNewPassword()));

        User savedUser = userRepository.save(currentUser);

        emailService.sendPasswordChangedEmail(savedUser);

    }

}
