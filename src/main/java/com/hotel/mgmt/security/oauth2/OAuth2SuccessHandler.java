package com.hotel.mgmt.security.oauth2;

import com.hotel.mgmt.config.ApplicationProperties;
import com.hotel.mgmt.security.AuthoritiesConstants;
import com.hotel.mgmt.security.jwt.TokenProvider;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ApplicationProperties properties;
    private final TokenProvider tokenProvider;

    public OAuth2SuccessHandler(ApplicationProperties properties, TokenProvider tokenProvider) {
        this.properties = properties;
        this.tokenProvider = tokenProvider;
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");
        String token = tokenProvider.createToken(email, AuthoritiesConstants.CUSTOMER);
        String uri = UriComponentsBuilder
            .fromUriString("http://" + properties.getHostName() + "/oauth2/redirect")
            .queryParam("token", token)
            .build()
            .toUriString();
        getRedirectStrategy().sendRedirect(request, response, uri);
    }
}
