package com.expensetracker.service;

import com.expensetracker.entity.User;
import com.expensetracker.enums.AuthProvider;
import com.expensetracker.enums.Role;
import com.expensetracker.repository.UserRepository;
import com.expensetracker.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");

        String fullName = oAuth2User.getAttribute("name");

        if (email == null || email.isBlank()) {

            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_user"), "Email not found from Google.");

        }

        User user = userRepository.findByEmail(email).orElseGet(() -> {

            User newUser = new User();

            newUser.setEmail(email);
            newUser.setFullName(fullName != null ? fullName : "Google User");
            newUser.setRole(Role.USER);
            newUser.setProvider(AuthProvider.GOOGLE);

            return userRepository.save(newUser);

        });

        return new UserPrincipal(user);

    }

}
