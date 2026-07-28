package com.expensetracker.dto.response;

import com.expensetracker.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String token;
    
    private String tokenType;

    private Long id;

    private String fullName;

    private String email;

    private Role role;
}
