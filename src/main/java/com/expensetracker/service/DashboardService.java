package com.expensetracker.service;

import com.expensetracker.dto.response.DashboardResponse;
import com.expensetracker.dto.response.RecentTransactionResponse;
import com.expensetracker.entity.User;

public interface DashboardService {

    DashboardResponse getDashboard(Integer month, Integer year);

}
