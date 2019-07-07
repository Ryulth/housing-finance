package kakaopay.housingfinance.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class JwtTokenCheckerTest {

    @InjectMocks
    JwtTokenChecker jwtTokenChecker;

    //No ExpirationTime
    static final String testJwtToken =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InRlc3RuYW1lIn0.peeInHnzEaPxq5doHrA_QzYFM6tRy4XjW57MEnfrICk";

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(jwtTokenChecker, "secretKey", "test");
    }

    @Test
    public void testIsUsable(){
        boolean isUsable = jwtTokenChecker.isUsable(testJwtToken);
        assertThat(isUsable,is(true));
    }

    @Test(expected = UnauthorizedException.class)
    public void testIsUsableThrowUnauthorizedException(){
        jwtTokenChecker.isUsable(testJwtToken+"fake");
    }


}
