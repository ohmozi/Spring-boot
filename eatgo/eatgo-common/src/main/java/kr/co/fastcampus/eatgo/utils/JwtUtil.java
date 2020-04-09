package kr.co.fastcampus.eatgo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {

    private Key key;
//        key를 32자리 이상써줘야한다

    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(Long userId, String name, Long restaurantId) {

        JwtBuilder builder = Jwts.builder()
                .claim("userId", userId)
                .claim("name", name);
        if (restaurantId != null){          //레스토랑 id가 있다면 즉, 로그인사용자가 가게주인이라면 레스토랑id존재
            builder = builder.claim("restaurantId", restaurantId);  //가게주인일 경우에 토큰에 레스토랑id도 포함해줌
        }

        return builder
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)  //jws는 sign이 포함된 jwt
                .getBody();

        return claims;
    }
    //test configuration
}
