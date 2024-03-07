package com.pos.javapos.authentication.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenBlacklist tokenBlacklist;
    @Autowired
    JwtDecoder accessTokenDecoder;
    @Autowired
    RouteValidator routeValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (validateRoute(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = extractTokenFromRequest(request);
        if (token != null && !tokenBlacklist.isBlacklisted(token)) {
            if (isTokenValidate(token)) {
                // Token is valid and not blacklisted
                // Proceed with request processing
                filterChain.doFilter(request, response);
            }
        }
            // Token is blacklisted or expired, deny access
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        // Get the Authorization header from the request
        String authorizationHeader = request.getHeader("Authorization");

        // Check if the Authorization header is not null and starts with "Bearer "
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            // Extract the JWT token (remove "Bearer " prefix)
            return authorizationHeader.substring(7);
        }

        // If the Authorization header is not valid, return null
        return null;
    }

    private Boolean isTokenValidate(String token) {
        Jwt jwt = accessTokenDecoder.decode(token);
        if (Objects.isNull(jwt)) {
            return false;
        }
        Instant now = Instant.now();
        Instant expiredAt = jwt.getExpiresAt();
        Duration duration = Duration.between(now,expiredAt);
        return duration.isPositive();
    }

    private Boolean validateRoute(HttpServletRequest request){
        String requestURI = request.getRequestURI(); // Get the requested URI

        // Define allowed paths for login, token, refresh, and register functionalities
        Set<String> allowedPaths = new HashSet<>(Arrays.asList("/api/auth/login", "/api/auth/refresh", "/api/auth/register"));

        // Check if the request path is allowed without token validation
        //filterChain.doFilter(request, response);
        return allowedPaths.contains(requestURI);
    }
}


