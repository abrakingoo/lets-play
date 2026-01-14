package abu.lets_play.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import abu.lets_play.Model.Enums.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private final SecretKey secretKey;

    public JwtUtil(@Value("${JWT_SECRET_STRING}") String jwtSecret) {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateWebToken(String Id, String email, Role role) {
        return Jwts.builder()
                .subject(Id)
                .claim("email", email)
                .claim("role", role.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3))
                .signWith(secretKey)
                .compact();
    }

    public SecretKey getKey() {
        return secretKey;
    }
    
}
