package com.expensetracker.service;

import com.expensetracker.dto.response.MonthlyReportEmailResponse;
import com.expensetracker.entity.User;

public interface EmailService {

    void sendSimpleEmail(
            String to,
            String subject,
            String body
    );

    void sendWelcomeEmail(User user);

    void sendPasswordChangedEmail(User user);

    void sendOtpEmail(User user, String otp);

    void sendMonthlyReportEmail(User user, MonthlyReportEmailResponse reportResponse);

}
