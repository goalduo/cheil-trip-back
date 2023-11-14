package com.goalduo.cheilTrip.jwt;

import com.goalduo.cheilTrip.member.dto.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ConfigurationProperties("spring.jwt")
@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Getter @Setter private String secretKey;
    private final long accessTokenExpiration = 1000L * 60 * 60; // 1시간
    private final long millisecondsInDay = 24 * 60 * 60 * 1000;
    private final long refreshTokenExpiration = 14 * millisecondsInDay;


    public Key encodedSecretKey() {
        return Keys
                .hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public JwtToken createJwtToken(Member loginMember) {
        Claims claims = Jwts.claims();
        claims.put("userId",loginMember.getUserId());
        claims.put("username",loginMember.getUserName());
        String accessToken = createToken(claims, accessTokenExpiration);
        String refreshToken = createToken(new HashMap<>(), refreshTokenExpiration);
        return JwtToken.builder()
                .userId(loginMember.getUserId())
                .userName(loginMember.getUserName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    public String createToken(Map<String, Object> claims, long expiration){
        return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + expiration))
                    .signWith(encodedSecretKey(), SignatureAlgorithm.HS256)
                    .compact();
    }

    public static void main(String[] args) {
        JwtProvider jwtProvider = new JwtProvider();
        Claims claims = Jwts.claims();
        claims.put("userId","ssafy");
        claims.put("username","wook2");
        System.out.println(jwtProvider.createToken(claims, 1000L * 60 * 60));
    }
}
