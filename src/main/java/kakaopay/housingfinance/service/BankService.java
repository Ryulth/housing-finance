package kakaopay.housingfinance.service;

import kakaopay.housingfinance.dto.BankDto;
import kakaopay.housingfinance.entity.Bank;
import kakaopay.housingfinance.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankService {
    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    // TODO MAPSTRUCT 구현
    public List<BankDto> getAllBanks() {
        List<Bank> banks = bankRepository.findAll();
        List<BankDto> bankDtoList = new ArrayList<>();
        for (Bank b : banks) {
            bankDtoList.add(
                    BankDto.builder()
                            .name(b.getName())
                            .build()
            );
        }
        return bankDtoList;
    }
}
