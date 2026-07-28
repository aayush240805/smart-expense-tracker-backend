package com.expensetracker.service;

import com.expensetracker.dto.otpRequest.ForgotPasswordRequest;
import com.expensetracker.dto.otpRequest.ResetPasswordRequest;

public interface OtpService {

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);

}
