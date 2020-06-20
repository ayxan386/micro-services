package org.aykhan.springdataprovidermongo.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aykhan.springdataprovidermongo.exception.auth.AuthException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        try {
            UsernamePasswordAuthenticationToken auth = Optional.of(jwtUtils.extractToken(req))
                    .filter(jwtUtils::validateToken)
                    .map(token -> {
                        String username = jwtUtils.extractUsername(token);
                        UserPrincipal principal = UserPrincipal
                                .builder()
                                .username(username)
                                .build();
                        return new UsernamePasswordAuthenticationToken(
                                principal, token, Collections.emptyList()
                        );
                    })
                    .orElseThrow(AuthException::new);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(req, res);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
