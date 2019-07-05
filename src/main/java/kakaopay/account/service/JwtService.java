package kakaopay.account.service;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService implements TokenService {
    private @Value("${jwt.secret}")
    String secretKey;
    private @Value("${jwt.expiration-time.access-token}")
    Integer accessTokenExpirationTime;
    private @Value("${jwt.expiration-time.refresh-token}")
    Integer refreshTokenExpirationTime;


    @Override
    public <T> Map<String, Object> publishToken(Map<String, Object> body, T subject) {
        String accessToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject.toString())
                .setClaims(body)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * accessTokenExpirationTime))
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject.toString())
                .setClaims(body)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * refreshTokenExpirationTime))
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token_type","bearer");
        resultMap.put("access_token", accessToken);
        resultMap.put("refresh_token", refreshToken);
        return resultMap;
    }

    @Override
    public String getUsernameFromToken(String token) throws IllegalAccessException {
        try {
            Claims claims = Jwts.parser().setSigningKey(this.generateKey())
                    .parseClaimsJws(token).getBody(); // 정상 수행된다면 해당 토큰은 정상토큰
            return claims.get("username").toString();
        } catch (ExpiredJwtException e) {
            throw new IllegalAccessException(e.getMessage());
        } catch (JwtException e) {
            throw new IllegalAccessException(e.getMessage());
        }
    }

    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = secretKey.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return key;
    }

}
