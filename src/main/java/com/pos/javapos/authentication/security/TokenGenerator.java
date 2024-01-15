package com.pos.javapos.authentication.security;

import com.pos.javapos.authentication.dto.RoleDto;
import com.pos.javapos.authentication.dto.TokenDto;
import com.pos.javapos.authentication.entity.Role;
import com.pos.javapos.authentication.service.RoleService;
import com.pos.javapos.authentication.service.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;
import com.pos.javapos.authentication.entity.User;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@Slf4j
public class TokenGenerator {
    @Autowired
    JwtEncoder accessTokenEncoder;
    @Autowired
    RoleService roleService;

    @Autowired
    @Qualifier("jwtRefreshTokenEncoder")
    JwtEncoder jwtRefreshTokenEncoder;

    private String createAccessToken(Authentication authentication){
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet
                .builder()
                .issuer(user.getUsername())
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(user.getId())
                .build();

        return accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    private String createRefreshToken(Authentication authentication){
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer(user.getUsername())
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.DAYS))
                .subject(user.getId())
                .build();
        return jwtRefreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    public TokenDto createToken(Authentication authentication){
        if (!(authentication.getPrincipal() instanceof UserDetailsImpl user )) {
            throw new BadCredentialsException(
                    MessageFormat.format("principal {0} is not of type User", authentication.getPrincipal().getClass())
            );
        }
        TokenDto tokenDto = new TokenDto();
        tokenDto.setUser_id(user.getId());
        tokenDto.setRole(user.getRole());
        tokenDto.setAccessToken(createAccessToken(authentication));
        String refreshToken;
        if(authentication.getCredentials() instanceof Jwt jwt) {
            Instant now = Instant.now();
            Instant expiresAt = jwt.getExpiresAt();
            Duration duration = Duration.between(now, expiresAt);
            long dayUntilExpired = duration.toDays();
            if(dayUntilExpired < 7){
                refreshToken = createRefreshToken(authentication);
            }else{
                refreshToken = jwt.getTokenValue();
            }
        }else{
            refreshToken = createRefreshToken(authentication);
        }
        tokenDto.setRefreshToken(refreshToken);
        return tokenDto;
    }
}
