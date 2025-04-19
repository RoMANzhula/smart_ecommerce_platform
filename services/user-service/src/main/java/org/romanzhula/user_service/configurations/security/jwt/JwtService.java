package org.romanzhula.user_service.configurations.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.romanzhula.user_service.exceptions.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecretCode;
    private final Instant issuedAt = LocalDateTime.now().toInstant(ZoneOffset.UTC);
    private final Instant expiration = issuedAt.plus(2, ChronoUnit.HOURS);


    public String generateToken(UserDetails userDetails) {
        Instant issuedAt = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        Instant expiration = issuedAt.plus(2, ChronoUnit.HOURS);

        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(getSecretKey())
                .compact()
        ;
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretCode.getBytes());
    }

    public String extractUsernameFromToken(String jwtToken) {
        try {
            return extractClaim(jwtToken, Claims::getSubject);
        } catch (Exception e) {
            throw new InvalidTokenException("Failed to extract username from JWT token");
        }
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> function) {
        Claims claims = extractAllClaims(jwtToken);

        return function.apply(claims);
    }

    public Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload()
        ;
    }

    public boolean isTokenValid(String jwtToken, String username) {
        var usernameFromToken = extractUsernameFromToken(jwtToken);

        if (isTokenExpired(jwtToken)) {
            throw new InvalidTokenException("JWT token has expired");
        }

        return usernameFromToken.equals(username) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

}
