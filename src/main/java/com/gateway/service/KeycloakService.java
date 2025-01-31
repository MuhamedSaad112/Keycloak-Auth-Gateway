package com.gateway.service;

import com.gateway.dto.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class KeycloakService {

    private final WebClient webClient;

    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
    private String tokenUrl;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Value("${custom.security.keycloak.logout-uri}")
    private String logoutUrl;

    private static final String GRANT_TYPE = "password";

    public KeycloakService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<AccessTokenResponse> getAccessToken(String username, String password, String otp) {
        String formEncodedBody = "client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) +
                "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8) +
                "&grant_type=" + URLEncoder.encode(GRANT_TYPE, StandardCharsets.UTF_8) +
                "&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8) +
                "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8) +
                "&otp=" + URLEncoder.encode(otp, StandardCharsets.UTF_8);

        return webClient.post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formEncodedBody)
                .retrieve()
                .bodyToMono(AccessTokenResponse.class)
                .doOnError(error -> {
                    throw new RuntimeException("Failed to get access token from Keycloak", error);
                });
    }

    public Mono<AccessTokenResponse> refreshAccessToken(String refreshToken) {
        String formEncodedBody = "client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) +
                "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8) +
                "&grant_type=" + URLEncoder.encode("refresh_token", StandardCharsets.UTF_8) +
                "&refresh_token=" + URLEncoder.encode(refreshToken, StandardCharsets.UTF_8);

        return webClient.post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formEncodedBody)
                .retrieve()
                .bodyToMono(AccessTokenResponse.class)
                .doOnError(error -> {
                    throw new RuntimeException("Failed to refresh access token from Keycloak", error);
                });
    }

    public Mono<Void> logout(String refreshToken) {
        String formEncodedBody = "client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) +
                "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8) +
                "&refresh_token=" + URLEncoder.encode(refreshToken, StandardCharsets.UTF_8);

        return webClient.post()
                .uri(logoutUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formEncodedBody)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(error -> {
                    throw new RuntimeException("Failed to logout from Keycloak", error);
                });
    }

}

