package com.gcrespo.demoparkapi.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public final class JwtUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    private static final String JWT_BEARER = "Bearer ";
    private static final String JWT_AUTHORIZATION = "Authorization";
    private static final String SECRET_KEY = "1Y90ZfcWwN2ONPweFMU33hMqHRacycFT";

    private static final long EXPIRE_DAYS = 0L;
    private static final long EXPIRE_HOURS = 0L;
    private static final long EXPIRE_MINUTES = 2L;

    private JwtUtils() {
    }


    public static JwtToken createToken(String username, String role) {

        Date issuedAt = new Date();
        Date limit = toExpireDate(issuedAt);

        String token = Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()
                .subject(username)
                .issuedAt(issuedAt)
                .expiration(limit)
                .signWith(generateKey())
                .claim("Role", role)
                .compact();

        return new JwtToken(token);
    }

    public static String getUsername(String token) {
        return Optional.ofNullable(getClaimsFromToken(token))
                .map(Claims::getSubject)
                .orElse(null);
    }

    public static boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(generateKey())
                    .build()
                    .parseSignedClaims(parseToken(token));
            return true;
        } catch (JwtException e) {
            LOGGER.error("Token is not valid {}", e.getMessage(), e);
        }
        return false;
    }

    private static Key generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static Date toExpireDate(Date start) {
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    private static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(generateKey())
                    .build()
                    .parseSignedClaims(parseToken(token))
                    .getPayload();
        } catch (JwtException e) {
            LOGGER.error("Token is not valid {}", e.getMessage(), e);
        }

        return null;
    }

    private static String parseToken(String token) {
        if (token.contains(JWT_BEARER))
            return token.substring(JWT_BEARER.length());
        return token;
    }

}
