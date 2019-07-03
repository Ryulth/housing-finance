package kakaopay.account.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JwtService {
    private @Value("${jwt.secret}")
    String secretKey;
    private @Value("${jwt.expiration-time}")
    Integer expirationTime;

    public <T> String publishToken(String key, T value, String subject){
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .claim(key, value)
                .setExpiration(new Date(System.currentTimeMillis() + 1000*expirationTime))
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
        return jwt;
    }

    private byte[] generateKey(){
        byte[] key = null;
        try {
            key = secretKey.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return key;
    }

}
