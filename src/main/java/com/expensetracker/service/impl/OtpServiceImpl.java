package com.expensetracker.service.impl;

import com.expensetracker.dto.otpRequest.ForgotPasswordRequest;
import com.expensetracker.dto.otpRequest.ResetPasswordRequest;
import com.expensetracker.entity.Otp;
import com.expensetracker.entity.User;
import com.expensetracker.exception.ResourceNotFoundException;
import com.expensetracker.repository.OtpRepository;
import com.expensetracker.repository.UserRepository;
import com.expensetracker.service.EmailService;
import com.expensetracker.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Clock;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final UserRepository userRepository;

    private final OtpRepository otpRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final SecureRandom secureRandom = new SecureRandom();


    private String otpGenerator() {

        return String.valueOf(secureRandom.nextInt(900000) + 100000);

    }


    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {

        // Find user
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found."));

        // Delete all old OTPs
        otpRepository.deleteByUser(user);

        // Generate new OTP
        String generatedOtp = otpGenerator();

        // Save OTP
        Otp otp = Otp.builder()
                .otp(generatedOtp)
                .expiryTime(LocalDateTime.now(Clock.systemDefaultZone()).plusMinutes(10))
                .verified(false)
                .user(user)
                .build();

        otpRepository.save(otp);

        // Send OTP via email
        emailService.sendOtpEmail(user, generatedOtp);

    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {

        // Find user
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found."));

        // Find latest OTP
        Otp otp = otpRepository.findTopByUserAndOtpAndVerifiedFalseOrderByCreatedAtDesc(user, request.getOtp()).orElseThrow(() -> new IllegalArgumentException("Invalid OTP."));

        // Check expiry
        if (otp.getExpiryTime().isBefore(LocalDateTime.now(Clock.systemDefaultZone()))) {

            throw new IllegalArgumentException("OTP has expired.");

        }

        // Check password confirmation
        if (!request.getNewPassword().equals(request.getConfirmedPassword())) {

            throw new IllegalArgumentException("New password and confirm password do not match.");

        }

        // Update password & save user
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        // Mark OTP as verified & save it
        otp.setVerified(true);

        otpRepository.save(otp);

        // Send confirmation email
        emailService.sendPasswordChangedEmail(user);
    }
}
