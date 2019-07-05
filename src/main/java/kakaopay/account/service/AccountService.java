package kakaopay.account.service;

import kakaopay.account.dto.UserDto;
import kakaopay.account.entity.User;
import kakaopay.account.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AccountService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public Map<String, Object> signUp(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new EntityExistsException("Username is duplicated");
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        userRepository.save(user);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", getToken(user));
        return resultMap;
    }

    public Map<String, Object> signIn(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", getToken(user));
        return resultMap;
    }

    private String getToken(User user) {
        Map<String, Object> body = Stream.of(
                new AbstractMap.SimpleEntry<>("username", user.getUsername()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return tokenService.publishToken(body, "userInfo");
    }

    public Map<String, Object> getRefreshToken(String originalToken) throws IllegalAccessException {
        String username = tokenService.getUsernameFromToken(originalToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        String refreshToken = getToken(user);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("refreshToken", refreshToken);
        return resultMap;
    }

}
