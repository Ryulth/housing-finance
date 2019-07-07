package kakaopay.account.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class JwtServiceTest {

    @InjectMocks
    JwtService jwtService;

    Map<String, Object> body = new HashMap<>();

    //No ExpirationTime
    static final String testJwtToken =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InRlc3RuYW1lIn0.peeInHnzEaPxq5doHrA_QzYFM6tRy4XjW57MEnfrICk";

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(jwtService, "secretKey", "test");
        ReflectionTestUtils.setField(jwtService, "accessTokenExpirationTime", 30);
        ReflectionTestUtils.setField(jwtService, "refreshTokenExpirationTime", 60);

        body.put("username","test");
    }

    @Test
    public void testPublishToken() throws UnsupportedEncodingException {
        Map<String, Object> resultMap = jwtService.publishToken(body,"userInfo");

        assertThat(resultMap, IsMapContaining.hasKey("token_type"));
        assertThat(resultMap, IsMapContaining.hasKey("access_token"));
        assertThat(resultMap, IsMapContaining.hasKey("refresh_token"));

        String accessToken = (String) resultMap.get("access_token");

        Jws<Claims> claims = Jwts.parser()
                .setSigningKey("test".getBytes("UTF-8"))
                .parseClaimsJws(accessToken);
        assertThat(claims.getBody().get("username"), is("test"));
    }

    @Test
    public void testGetUsernameFromToken() throws IllegalAccessException {
        String resultName = jwtService.getUsernameFromToken(testJwtToken);
        assertThat(resultName, is("testname"));
    }

    @Test(expected = IllegalAccessException.class)
    public void testGetUsernameFromTokenThrowIllegalAccessException() throws IllegalAccessException {
        String resultName = jwtService.getUsernameFromToken(testJwtToken+"fake");
    }

}