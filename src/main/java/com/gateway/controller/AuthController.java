package com.gateway.controller;

import com.gateway.dto.AccessTokenResponse;
import com.gateway.dto.JwtRequest;
import com.gateway.dto.RefreshTokenRequest;
import com.gateway.service.KeycloakService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final KeycloakService keycloakService;

    public AuthController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping("/token")
    public Mono<AccessTokenResponse> getToken(@RequestBody JwtRequest jwtRequest) {
        return keycloakService.getAccessToken(
                jwtRequest.getUsername(),
                jwtRequest.getPassword(),
                jwtRequest.getOtp()
        );
    }

    @PostMapping("/refresh")
    public Mono<ResponseEntity<AccessTokenResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {
        return keycloakService.refreshAccessToken(request.getRefreshToken())
                .map(ResponseEntity::ok);
    }

    @PostMapping("/logout")
    public Mono<ResponseEntity<String>> logout(@RequestBody RefreshTokenRequest request) {
        return keycloakService.logout(request.getRefreshToken())
                .then(Mono.just(ResponseEntity.ok("User logged out successfully")));
    }
}
