package is.hi.hbv501g.verkefni.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class jwtService {
    private final SecretKey secretKey;

    public jwtService(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());

    }

    public String generateToken(String subject, Map<String, Object> claims) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(subject)
                .addClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(3600)))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            String compact = token.startsWith("Bearer ") ? token.substring(7) : token;
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(compact);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractRole(String token) {
        String compact = token.startsWith("Bearer ") ? token.substring(7) : token;
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(compact).getBody();
        Object role = claims.get("role");
        return role != null ? role.toString() : null;
    }

    public String extractEmail(String token) {
        String compact = token.startsWith("Bearer ") ? token.substring(7) : token;
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(compact).getBody();
        return claims.getSubject();
    }

}
