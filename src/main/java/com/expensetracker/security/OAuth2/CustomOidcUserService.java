package com.expensetracker.security.OAuth2;

import com.expensetracker.entity.User;
import com.expensetracker.enums.AuthProvider;
import com.expensetracker.enums.Role;
import com.expensetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final UserRepository userRepository;

    @Override
    public OidcUser loadUser(
            OidcUserRequest oidcUserRequest
    ) throws OAuth2AuthenticationException {

        OidcUser oidcUser = super.loadUser(oidcUserRequest);

        String email = oidcUser.getAttribute("email");

        String fullName = oidcUser.getAttribute("name");

        String picture = oidcUser.getAttribute("picture");

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

        return new CustomOidcUserPrincipal(user, oidcUser);

    }

}
