package com.expensetracker.scheduler;

import com.expensetracker.service.MonthlyReportEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MonthlyReportEmailScheduler {

    private final MonthlyReportEmailService monthlyReportEmailService;

    @Scheduled(cron = "0 0 9 1 * *")
    public void send() {

        log.info("Starting Monthly Report Scheduler...");

        monthlyReportEmailService.sendMonthlyReportEmail();

        log.info("Monthly Report Scheduler Completed.");

    }

}
