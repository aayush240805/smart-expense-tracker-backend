package com.expensetracker.security.OAuth2;

import com.expensetracker.entity.User;
import com.expensetracker.enums.AuthProvider;
import com.expensetracker.enums.Role;
import com.expensetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");

        String fullName = oAuth2User.getAttribute("name");

        String picture = oAuth2User.getAttribute("picture");

        if (email == null || email.isBlank()) {

            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_user"), "Email not found from Google.");

        }

        User user = userRepository.findByEmail(email).orElseGet(() -> {

            User newUser = new User();

            newUser.setEmail(email);

            newUser.setFullName(fullName != null
                    ? fullName
                    : "Google User"
            );

            newUser.setProfilePicture(picture);

            newUser.setRole(Role.USER);

            newUser.setProvider(AuthProvider.GOOGLE);

            newUser.setCreatedAt(LocalDateTime.now(Clock.systemDefaultZone()));

            newUser.setUpdatedAt(LocalDateTime.now(Clock.systemDefaultZone()));

            return userRepository.save(newUser);

        });

        return new CustomOAuth2UserPrincipal(user, oAuth2User.getAttributes());

    }

}
