package org.aykhan.dataprovider.security;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.aykhan.dataprovider.exception.unauth.InvalidTokenException;
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
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
  private final JwtUtils jwtUtils;

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
    UsernamePasswordAuthenticationToken auth = Optional.of(jwtUtils.extractToken(req))
        .filter(token1 -> {
          try {
            return jwtUtils.validateToken(token1);
          } catch (ExpiredJwtException e) {
            return false;
          }
        })
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
        .orElseThrow(InvalidTokenException::new);
    SecurityContextHolder.getContext().setAuthentication(auth);
    filterChain.doFilter(req, res);
  }
}
