package kakaopay.account.service;

import kakaopay.account.dto.UserDto;
import kakaopay.account.entity.User;
import kakaopay.account.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityExistsException;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    UserRepository userRepository;
    @Mock
    TokenService tokenService;

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    static final UserDto userDto = UserDto.builder()
            .username("test")
            .password("1234")
            .build();
    //No ExpirationTime
    static final String testJwtToken =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InRlc3RuYW1lIn0.peeInHnzEaPxq5doHrA_QzYFM6tRy4XjW57MEnfrICk";

    @Test
    public void testSignUp() {
        Map<String, Object> resultMap = accountService.signUp(userDto);
        verify(userRepository, atLeastOnce()).save(any());
        assertThat(resultMap, is(notNullValue()));
    }

    @Test(expected = EntityExistsException.class)
    public void testSignUpThrowEntityExistsException() {
        when(userRepository.existsByUsername(userDto.getUsername()))
                .thenReturn(true);

        accountService.signUp(userDto);
    }

    @Test
    public void testSignIn() {
        when(userRepository.findByUsername(userDto.getUsername()))
                .thenReturn(Optional.of(User.builder()
                        .id(1L)
                        .username("test")
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .build()));
        accountService.signIn(userDto);
        verify(userRepository, atLeastOnce()).findByUsername(userDto.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testSignInThrowUsernameNotFoundException(){
        accountService.signIn(userDto);
        verify(userRepository, atLeastOnce()).findByUsername(userDto.getUsername());
    }

    @Test(expected = BadCredentialsException.class)
    public void testSignInThrowBadCredentialsException(){
        when(userRepository.findByUsername(userDto.getUsername()))
                .thenReturn(Optional.of(User.builder()
                        .id(1L)
                        .username("test")
                        .password(passwordEncoder.encode(userDto.getPassword()+"fake"))
                        .build()));
        accountService.signIn(userDto);
        verify(userRepository, atLeastOnce()).findByUsername(userDto.getUsername());
    }

    @Test
    public void testUpdateAccessToken() throws IllegalAccessException {
        when(tokenService.getUsernameFromToken(testJwtToken))
                .thenReturn("test");
        when(userRepository.findByUsername(userDto.getUsername()))
                .thenReturn(Optional.of(User.builder()
                        .id(1L)
                        .username("test")
                        .password(passwordEncoder.encode(userDto.getPassword()+"fake"))
                        .build()));
        accountService.updateAccessToken(testJwtToken);
        verify(userRepository, atLeastOnce()).findByUsername(userDto.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testUpdateAccessTokenThrowUsernameNotFoundException() throws IllegalAccessException {
        when(tokenService.getUsernameFromToken(testJwtToken))
                .thenReturn("test");
        accountService.updateAccessToken(testJwtToken);
        verify(userRepository, atLeastOnce()).findByUsername(userDto.getUsername());
    }

}