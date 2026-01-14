package abu.lets_play.Config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import abu.lets_play.Security.JwtUtil;
import abu.lets_play.Service.TokenBlacklistService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final TokenBlacklistService tokenBlacklistService;

    public JwtAuthFilter(JwtUtil jwtUtil, TokenBlacklistService tokenBlacklistService) {
        this.jwtUtil = jwtUtil;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (tokenBlacklistService.isBlacklisted(token)) {
                filterChain.doFilter(request, response);
                return;
            }
            try {
                Claims claims = Jwts.parser()
                    .verifyWith(jwtUtil.getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

                String role = claims.get("role", String.class);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(), null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                // Invalid token - continue without authentication
            }
        }
        filterChain.doFilter(request, response);
    }
}
