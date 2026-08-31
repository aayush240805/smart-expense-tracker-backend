package com.expensetracker.security.OAuth2;

import com.expensetracker.entity.User;
import com.expensetracker.security.JwtService;
import com.expensetracker.security.UserPrincipal;
import com.expensetracker.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class  OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;

    private final EmailService emailService;

    @Value("${app.OAuth2-redirect-success-url}")
    private String redirectSuccessUrl;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        CustomOidcUserPrincipal oidcUserPrincipal = (CustomOidcUserPrincipal) authentication.getPrincipal();

        User user = oidcUserPrincipal.getUser();

        if (user.getCreatedAt() ==null){
            // Send login email
            emailService.sendGoogleLoginEmail(user);
        }

        UserPrincipal userPrincipal = new UserPrincipal(user);

        String token = jwtService.generateToken(userPrincipal);

        String redirectUrl = redirectSuccessUrl
                + URLEncoder.encode(token, StandardCharsets.UTF_8);

        getRedirectStrategy().sendRedirect(
                request,
                response,
                redirectUrl
        );

    }

}
