package kakaopay.account.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TokenService {
    <T> String publishToken(Map<String,Object> body, T subject);
    String getUsernameFromToken(String token) throws IllegalAccessException;
}
