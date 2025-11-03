package com.springboot.hospitalmgmt.HospitalManagement.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String name = oauthUser.getAttribute("name");
        String email = oauthUser.getAttribute("email");

        // Optional: persist or update user in DB here

        // URL-encode parameters to avoid redirect issues with spaces/characters
        String qName = URLEncoder.encode(name != null ? name : "", StandardCharsets.UTF_8);
        String qEmail = URLEncoder.encode(email != null ? email : "", StandardCharsets.UTF_8);

        // Redirect to controller endpoint that you already expose: /api/auth/oauth2/success
        response.sendRedirect("/api/auth/oauth2/success?name=" + qName + "&email=" + qEmail);
    }
}
