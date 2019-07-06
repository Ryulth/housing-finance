package kakaopay.housingfinance.security;

import org.springframework.stereotype.Service;


@Service
public interface TokenChecker {
    boolean isUsable(String jwt);
}
