package com.expensetracker.security.OAuth2;

import com.expensetracker.entity.User;
import com.expensetracker.security.JwtService;
import com.expensetracker.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        CustomOidcUserPrincipal oidcUserPrincipal = (CustomOidcUserPrincipal) authentication.getPrincipal();

        User user = oidcUserPrincipal.getUser();

        UserPrincipal userPrincipal = new UserPrincipal(user);

        String token = jwtService.generateToken(userPrincipal);

        String redirectUrl = "http://localhost:5173/oauth2/redirect?token="
                + URLEncoder.encode(token, StandardCharsets.UTF_8);

        getRedirectStrategy().sendRedirect(
                request,
                response,
                redirectUrl
        );

    }

}
