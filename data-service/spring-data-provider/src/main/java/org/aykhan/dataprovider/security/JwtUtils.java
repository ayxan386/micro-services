package org.aykhan.dataprovider.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.aykhan.dataprovider.exception.notfound.TokenNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtUtils {

  @Value("${SECRET_KEY:secret}")
  private String SECRET_KEY = "secret";
  private static final String bearer = "Bearer ";

  public String extractToken(HttpServletRequest req) {
    String token = req.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.isEmpty(token) || !token.contains(bearer))
      throw new TokenNotFoundException();

    return token.substring(bearer.length());
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
  }

  public String extractUsername(String token) {
    return extractAllClaims(token).getSubject();
  }

  public boolean isExpired(String token) {
    return extractAllClaims(token)
        .getExpiration()
        .before(new Date());
  }

  public boolean validateToken(String token) {
    return !isExpired(token);
  }
}
