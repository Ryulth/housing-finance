package kakaopay.account.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Component
public class JwtService implements TokenService{
    private @Value("${jwt.secret}")
    String secretKey;
    private @Value("${jwt.expiration-time}")
    Integer expirationTime;

    public <T> String publishToken(Map<String,Object> body,T subject){

        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject.toString())
                .setClaims(body)
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
