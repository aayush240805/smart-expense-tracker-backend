package com.expensetracker.util;

import com.expensetracker.entity.User;
import com.expensetracker.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CurrentUserService {

    // Get logged-in user from JWT
    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            return userPrincipal.getUser();
        }

        // Throws a clean 401 Unauthorized error instead of crashing with a ClassCastException
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
    }

}
