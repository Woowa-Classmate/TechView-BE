package com.interview.techview.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
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
        return buildToken(userId, role, accessExpiration);
    }

    // RefreshToken 생성 (role 필요 없음)
    public String createRefreshToken(Long userId) {
        return buildToken(userId, null, refreshExpiration);
    }

    // 내부 공통 빌더
    private String buildToken(Long userId, String role, long expire) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expire));

        if (role != null)
            builder.claim("role", role);

        return builder.signWith(key, SignatureAlgorithm.HS256).compact();
    }

    // userId 추출
    public Long getUserId(String token) {
        return Long.valueOf(parseClaims(token).getSubject());
    }

    // role 추출
    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // 유효성 검증
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
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
}
