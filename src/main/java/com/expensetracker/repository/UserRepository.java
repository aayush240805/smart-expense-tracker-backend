package com.expensetracker.repository;

import com.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // dozens of methods already inherited from JpaRepository
    // these are custom methods:

    // find by id
    Optional<User> findById(Long id);

    // to avoid messy null checks
    Optional<User> findByEmail(String email);

    // is already registered by email
    Boolean existsByEmail(String email);

}
