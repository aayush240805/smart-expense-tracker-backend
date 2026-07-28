package com.expensetracker.repository;

import com.expensetracker.entity.Otp;
import com.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findTopByUserOrderByCreatedAtDesc(User user);

    Optional<Otp> findTopByUserAndOtpAndVerifiedFalseOrderByCreatedAtDesc(User user, String otp);

    void deleteByUser(User user);
}
