package com.interview.techview.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")              // 비밀키
    private String secretKey;

    @Value("${jwt.access-expiration}")   // AccessToken 만료시간(ms)
    private long accessExpiration;

    @Value("${jwt.refresh-expiration}")  // RefreshToken 만료시간(ms)
    private long refreshExpiration;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // AccessToken 생성
    public String createAccessToken(Long userId, String role) {
        return buildToken(userId, role, accessExpiration, "access");
    }

    // RefreshToken 생성 (role 필요 없음)
    public String createRefreshToken(Long userId) {
        return buildToken(userId, null, refreshExpiration, "refresh");
    }

    // 내부 공통 빌더
    private String buildToken(Long userId, String role, long expire, String type) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expire))
                .claim("type", type);

        if (role != null)
            builder.claim("role", role);

        return builder.signWith(key, SignatureAlgorithm.HS256).compact();
    }

    // userId 추출
    public Long getUserId(String token) {
        Claims claims = parseClaims(token);

        String subject = claims.getSubject();
        if (subject == null) {
            throw new JwtException("Invalid token");
        }

        return Long.valueOf(subject);
    }

    // role 추출
    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // 유효성 검증
    public void validateAccessToken(String token) {
        Claims claims = parseClaims(token);

        String role = claims.get("role", String.class);
        if (role == null) {
            throw new JwtException("Access token missing role");
        }
    }

    public void validateRefreshToken(String token) {
        Claims claims = parseClaims(token);

        String type = claims.get("type", String.class);
        if (!"refresh".equals(type)) {
            throw new JwtException("Not a refresh token");
        }
    }


    // Claims 파싱
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // RefreshToken 만료일 설정
    public LocalDateTime getRefreshExpiryDate() {
        return LocalDateTime.now().plusSeconds(refreshExpiration / 1000);
    }
}
