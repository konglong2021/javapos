package com.pos.javapos.helper;

import com.pos.javapos.authentication.service.UserDetailsImpl;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() != "anonymousUser") {
            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
            return Optional.of(user.getId());
        }else {
            return Optional.of("0");
        }
    }
}
