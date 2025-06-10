package dev.bast.ecommerce.usuarios.security.jwt;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import dev.bast.ecommerce.usuarios.security.services.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParser;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwtSecret:defaultSecretKeyWhichShouldBeChangedInProduction}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs:86400000}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserNameFromJwtToken(String token) {
        return getJwtParser().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            getJwtParser().parse(token);
            return true;
        } catch (Exception e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        }
        return false;
    }

    private JwtParser getJwtParser() {
        return Jwts.parser().setSigningKey(getSigningKey()).build();
    }
}
