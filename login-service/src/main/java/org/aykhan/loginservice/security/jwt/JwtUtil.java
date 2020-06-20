package org.aykhan.loginservice.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.aykhan.loginservice.abstractimplementation.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Service
@PropertySource("classpath:security.properties")
@Slf4j
public class JwtUtil {

  private static final long EXPIRATION = 5 * 60 * 60 * 1000;
  @Value(value = "${jwt.secret}")
  private String SECRET_KEY;

  public String generateToken(UserDetails userDetails) {
    HashMap<String, Object> claims = new HashMap<>();
    claims.put("roles", userDetails
        .getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(joining(" ")));
    return createToken(claims, userDetails.getUsername());
  }

  private String createToken(Map<String, Object> claims, String username) {
    return Jwts.builder()
        .addClaims(claims)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
        .setSubject(username)
        .signWith(HS256, SECRET_KEY)
        .compact();
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parser()
        .setSigningKey(HS256.getValue())
        .parseClaimsJws(token)
        .getBody();
  }

  public String extractUsername(String token) {
    return extractAllClaims(token).getSubject();
  }

  public List<String> extractRoles(String token) {
    return Optional.ofNullable(extractAllClaims(token)
        .get("roles"))
        .filter(obj -> obj instanceof List)
        .map(obj -> (List) obj)
        .filter(list -> list.size() > 0)
        .filter(list -> list.get(0) instanceof Role)
        .map(obj -> (List<Role>) obj)
        .map(list -> list
            .stream()
            .map(Role::getAuthority)
            .collect(toList())
        )
        .orElse(emptyList());
  }

  public boolean isExpired(String token) {
    return extractAllClaims(token)
        .getExpiration()
        .before(new Date());
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    return userDetails.getUsername().equals(extractUsername(token)) &&
        !isExpired(token);
  }
}
