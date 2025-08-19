package com.example.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * A service for handling JSON Web Tokens (JWTs), including creation and validation.
 */
public class TokenService {

    // IMPORTANT: This key should be stored securely and not hardcoded.
    // For production, use environment variables or a secret management service.
    private static final Key SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // Token validity in milliseconds (e.g., 24 hours)
    private static final long EXPIRATION_TIME_MS = 1000 * 60 * 60 * 24;

    /**
     * Generates a JWT for a given username.
     *
     * @param username The subject for whom the token is being generated.
     * @return A signed JWT string.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(SIGNING_KEY)
                .compact();
    }

    /**
     * Extracts the username (subject) from the JWT.
     *
     * @param token The JWT string.
     * @return The username.
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Validates the token's integrity and expiration.
     *
     * @param token The JWT string.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            // Log the exception for debugging, e.g., using a logging framework
            System.err.println("Invalid JWT token: " + e.getMessage());
        }
        return false;
    }

    /**
     * Retrieves the signing key. In a real application, this might involve
     * more complex logic, like fetching from a key store.
     *
     * @return The signing key.
     */
    public Key getSigningKey() {
        return SIGNING_KEY;
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}


