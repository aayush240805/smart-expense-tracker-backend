package com.expensetracker.service.impl;

import com.expensetracker.dto.reportResponse.BudgetReportResponse;
import com.expensetracker.dto.reportResponse.CategoryExpenseResponse;
import com.expensetracker.dto.response.MonthlyReportEmailResponse;
import com.expensetracker.entity.User;
import com.expensetracker.service.EmailService;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Month;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final Resend resend;

    @Value("${resend.from-email}")
    private String fromEmail;


    @Override
    public void sendSimpleEmail(String to, String subject, String body) {

        CreateEmailOptions message = CreateEmailOptions.builder()
                .from(fromEmail)
                .to(to)
                .subject(subject)
                .text(body)
                .build();

        try {

            CreateEmailResponse response =
                    resend.emails().send(message);

            log.info("Email sent successfully. ID: {}", response.getId());

        } catch (ResendException e) {

            throw new RuntimeException(
                    "Failed to send email: "
                            + e.getMessage(),
                    e
            );
        }

    }

    @Override
    @Async
    public void sendWelcomeEmail(User user) {

        String subject = "Welcome to Smart Expense Tracker";

        String body = """
            Hello %s,

            Welcome to Smart Expense Tracker!

            Your account has been created successfully.

            We are excited to help you manage your income, expenses, and budgets.

            Happy Saving!

            Regards,
            Smart Expense Tracker Team
            """.formatted(user.getFullName());

        sendSimpleEmail(
                user.getEmail(),
                subject,
                body
        );

    }

    @Override
    @Async
    public void sendGoogleLoginEmail(User user) {

        String subject = "Welcome to Smart Expense Tracker";

        String body = """
            Hello %s,

            Welcome to Smart Expense Tracker!

            You have successfully logged in to Smart Expense Tracker using Google.

            We are excited to help you manage your income, expenses, and budgets.

            Happy Saving!

            Regards,
            Smart Expense Tracker Team
            """.formatted(user.getFullName());

        sendSimpleEmail(
                user.getEmail(),
                subject,
                body
        );

    }


    @Override
    @Async
    public void sendPasswordChangedEmail(User user) {

        String subject = "Password Changed Successfully";

        String body = """
            Hello %s,

            Your password has been changed successfully.

            If you did not perform this action, please change your password immediately or contact support.

            Regards,
            Smart Expense Tracker Team
            """.formatted(user.getFullName());

        sendSimpleEmail(
                user.getEmail(),
                subject,
                body
        );

    }

    @Override
    @Async
    public void sendOtpEmail(User user, String otp) {
        String subject = "Password Reset OTP";

        String body = """
            Hello %s,

            Your OTP for password reset is:

            %s

            This OTP is valid for 10 minutes.

            If you didn't request this, please ignore this email.

            Regards,
            Smart Expense Tracker Team
            """.formatted(user.getFullName(), otp);

        sendSimpleEmail(
                user.getEmail(),
                subject,
                body
        );

    }

    @Override
    @Async
    public void sendMonthlyReportEmail(User user, MonthlyReportEmailResponse reportResponse) {

        StringBuilder message = new StringBuilder();

        message.append("Hello ")
                .append(user.getFullName())
                .append(",\n\n");

        message.append("Here is your monthly financial report for ")
                .append(Month.of(reportResponse.getMonth()))
                .append(" ")
                .append(reportResponse.getYear())
                .append(".\n\n");

        message.append("---------------------------------\n");
        message.append("SUMMARY\n");
        message.append("---------------------------------\n");

        message.append("Total Income  : ₹")
                .append(reportResponse.getTotalIncome())
                .append("\n");

        message.append("Total Expense : ₹")
                .append(reportResponse.getTotalExpense())
                .append("\n");

        message.append("Savings       : ₹")
                .append(reportResponse.getTotalSavings())
                .append("\n\n");

        message.append("---------------------------------\n");
        message.append("CATEGORY-WISE EXPENSES\n");
        message.append("---------------------------------\n");

        for (CategoryExpenseResponse expense : reportResponse.getCategoryWiseExpenses()) {

            message.append(expense.getCategory())
                    .append(" : ₹")
                    .append(expense.getTotalSpent())
                    .append("\n");
        }

        message.append("\n");

        message.append("---------------------------------\n");
        message.append("BUDGET REPORT\n");
        message.append("---------------------------------\n");

        for (BudgetReportResponse budget : reportResponse.getBudgetReports()) {

            message.append(budget.getCategory())
                    .append("\n");

            message.append("  Limit     : ₹")
                    .append(budget.getMonthlyLimit())
                    .append("\n");

            message.append("  Spent     : ₹")
                    .append(budget.getSpent())
                    .append("\n");

            message.append("  Remaining : ₹")
                    .append(budget.getRemaining())
                    .append("\n\n");
        }

        message.append("Keep tracking your expenses and have a great month!\n\n");

        message.append("Regards,\n");
        message.append("Smart Expense Tracker");

        sendSimpleEmail(
                user.getEmail(),
                "Monthly Expense Report - " + Month.of(reportResponse.getMonth()) + " " + reportResponse.getYear(),
                message.toString()
        );

    }


}