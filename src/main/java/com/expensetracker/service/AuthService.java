package com.expensetracker.service;

import com.expensetracker.dto.request.LoginRequest;
import com.expensetracker.dto.request.RegisterRequest;
import com.expensetracker.dto.response.ApiResponse;
import com.expensetracker.dto.response.LoginResponse;
import org.apache.coyote.BadRequestException;

public interface AuthService {

    ApiResponse register(RegisterRequest request) throws BadRequestException;

    LoginResponse login(LoginRequest request);

}
