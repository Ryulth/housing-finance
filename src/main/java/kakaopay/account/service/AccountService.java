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
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AccountService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
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

        String jwtToken = jwtService.publishToken("username",user.getUsername(),"userInfo");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("jwt", jwtToken);
        return resultMap;
    }

    public Map<String, Object> signIn(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        String jwtToken = jwtService.publishToken("username",user.getUsername(),"userInfo");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("jwt", jwtToken );
        return resultMap;
    }

}
