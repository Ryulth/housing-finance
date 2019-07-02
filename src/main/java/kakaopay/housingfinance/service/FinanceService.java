package kakaopay.housingfinance.service;

import kakaopay.housingfinance.entity.Bank;
import kakaopay.housingfinance.entity.BankFinance;
import kakaopay.housingfinance.pojo.FinanceStatusByYear;
import kakaopay.housingfinance.repository.BankFinanceRepository;
import kakaopay.housingfinance.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinanceService {
    private final BankRepository bankRepository;
    private final BankFinanceRepository bankFinanceRepository;

    public FinanceService(BankRepository bankRepository, BankFinanceRepository bankFinanceRepository) {
        this.bankRepository = bankRepository;
        this.bankFinanceRepository = bankFinanceRepository;
    }

    public Map<String, Object> getFinanceStatus() {
        List<Bank> banks = bankRepository.findAll();
        List<FinanceStatusByYear> financeStatusByYears = new ArrayList<>();

        for (Bank bank : banks) {
            List<BankFinance> bankFinances = bankFinanceRepository.findAllByBankId(bank.getId());
            Map<Integer, List<BankFinance>> bankFinanceMap = filterByYear(bankFinances);
            System.out.println(bank.getName());
            
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("name", "주택금융 공급현황");
        return resultMap;
    }

    private Map<Integer, List<BankFinance>> filterByYear(List<BankFinance> bankFinances) {
        Map<Integer, List<BankFinance>> bankFinanceMap = new HashMap<>();
        for (BankFinance bankFinance : bankFinances) {
            bankFinanceMap.putIfAbsent(bankFinance.getYear(), new ArrayList<>());
            bankFinanceMap.get(bankFinance.getYear())
                    .add(bankFinance);
        }
        return bankFinanceMap;
    }

}
