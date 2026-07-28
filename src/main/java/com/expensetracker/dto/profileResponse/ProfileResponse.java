package com.expensetracker.dto.profileResponse;

import com.expensetracker.enums.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {

    private Long id;

    private String fullName;

    private String email;

    private Role role;

    private LocalDateTime createdAt;

}
