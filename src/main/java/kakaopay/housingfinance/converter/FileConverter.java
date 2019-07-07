package kakaopay.housingfinance.converter;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FileConverter {
    List<List<String>> convertFile();
}
