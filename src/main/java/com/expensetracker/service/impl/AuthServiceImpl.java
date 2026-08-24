package com.expensetracker.service.impl;

import com.expensetracker.dto.request.LoginRequest;
import com.expensetracker.dto.request.RegisterRequest;
import com.expensetracker.dto.response.ApiResponse;
import com.expensetracker.dto.response.LoginResponse;
import com.expensetracker.entity.User;
import com.expensetracker.enums.AuthProvider;
import com.expensetracker.enums.Role;
import com.expensetracker.exception.BadRequestException;
import com.expensetracker.exception.DuplicateResourceException;
import com.expensetracker.repository.UserRepository;
import com.expensetracker.security.JwtService;
import com.expensetracker.security.UserPrincipal;
import com.expensetracker.service.AuthService;
import com.expensetracker.service.EmailService;
import com.expensetracker.util.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CurrentUserService currentUserService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final EmailService emailService;

    @Override
    @Transactional
    public ApiResponse register(RegisterRequest request) throws BadRequestException {

        // Only checking for throwing error
        Optional<User> userExist = userRepository.findByEmail(request.getEmail());

        if (userExist.isPresent()) {
            throw new DuplicateResourceException("User already registered.");
        }

        User newUser = new User();
        newUser.setFullName(request.getFullName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(Role.USER);
        newUser.setProvider(AuthProvider.APPLICATION);
        newUser.setCreatedAt(LocalDateTime.now(Clock.systemDefaultZone()));
        newUser.setUpdatedAt(LocalDateTime.now(Clock.systemDefaultZone()));

        User savedUser = userRepository.save(newUser);

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

    @Override
    public void deleteUser() {

        User currentUser = currentUserService.getCurrentUser();

        userRepository.deleteById(currentUser.getId());

    }

}
