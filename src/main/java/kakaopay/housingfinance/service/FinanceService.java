package kakaopay.housingfinance.service;

import kakaopay.housingfinance.entity.BankFinance;
import kakaopay.housingfinance.repository.BankFinanceRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinanceService {
    private final BankFinanceRepository bankFinanceRepository;

    public FinanceService(BankFinanceRepository bankFinanceRepository) {
        this.bankFinanceRepository = bankFinanceRepository;
    }

    public Map<String,Object> getFinanceStatus(){
        List<BankFinance> bankFinances = bankFinanceRepository.findAll();


        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("name","주택금융 공급현황");
        return resultMap;
    }
}
