package org.aykhan.springdataprovidermongo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.aykhan.springdataprovidermongo.exception.notfound.TokenNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
public class JwtUtils {

    private static final String SECRET_KEY = "secret";
    private static final long EXPIRATION = 5 * 60 * 60 * 1000;
    private final String bearer = "Bearer ";

    public String extractToken(HttpServletRequest req) {
        String token = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(token) || !token.contains(bearer))
            throw new TokenNotFoundException();

        return token.substring(bearer.length());
    }

    public String generateToken(UserDetails userDetails) {
        return createToken(new HashMap<String, Object>(), userDetails.getUsername());
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
