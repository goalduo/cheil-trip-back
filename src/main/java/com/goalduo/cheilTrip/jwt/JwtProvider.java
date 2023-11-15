package com.goalduo.cheilTrip.jwt;

import com.goalduo.cheilTrip.member.dto.Member;
import com.goalduo.cheilTrip.util.ApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ConfigurationProperties("spring.jwt")
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

    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claims = Jwts
                    .parserBuilder()
                    .setSigningKey(encodedSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        } catch (Exception e){
            log.info("JWT 파싱 에러", e);
        }
        return false;
    }
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(encodedSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

//    public static void main(String[] args) {
//        JwtProvider jwtProvider = new JwtProvider();
//        Claims claims = Jwts.claims();
//        claims.put("userId","ssafy");
//        claims.put("username","wook2");
//        System.out.println(jwtProvider.createToken(claims, 1000L * 60 * 60));
//    }
}
