package com.expensetracker.service.impl;

import com.expensetracker.dto.request.LoginRequest;
import com.expensetracker.dto.request.RegisterRequest;
import com.expensetracker.dto.response.ApiResponse;
import com.expensetracker.dto.response.LoginResponse;
import com.expensetracker.entity.User;
import com.expensetracker.enums.Role;
import com.expensetracker.repository.UserRepository;
import com.expensetracker.security.JwtService;
import com.expensetracker.security.UserPrincipal;
import com.expensetracker.service.AuthService;
import com.expensetracker.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final EmailService emailService;

    @Override
    @Transactional
    public ApiResponse register(RegisterRequest request) throws BadRequestException {

        if (Boolean.TRUE.equals(userRepository.existsByEmail(request.getEmail()))) {
            throw new BadRequestException("Email is already registered.");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now(Clock.systemDefaultZone()));
        user.setUpdatedAt(LocalDateTime.now(Clock.systemDefaultZone()));

        User savedUser = userRepository.save(user);

        emailService.sendWelcomeEmail(savedUser);

        return ApiResponse.builder()
                .success(true)
                .message("User registered successfully.")
                .build();
    }


    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        // this verifies email exists and password is correct
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        // After successful authentication, Spring returns your UserPrincipal because your CustomUserDetailsService returned: return new UserPrincipal(user)
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // generateToken() expects a UserDetails, and UserPrincipal implements UserDetails.
        String token = jwtService.generateToken(userPrincipal);

        // getting user entity
        User user = userPrincipal.getUser();

        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
