package kakaopay.account.service;

import kakaopay.account.dto.UserDto;
import kakaopay.account.entity.User;
import kakaopay.account.repository.UserRepository;
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
    public AccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public Map signUp(UserDto userDto) {
        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new EntityExistsException("Email is duplicated");
        }

        User user = User.builder()
                .email(userDto.getEmail())
                .password( passwordEncoder.encode(userDto.getPassword()))
                .build();

        userRepository.save(user);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("jwt", "token");
        return resultMap;
    }
}
