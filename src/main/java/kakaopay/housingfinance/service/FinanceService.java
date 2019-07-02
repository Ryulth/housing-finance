package kakaopay.housingfinance.service;

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

    /*
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
    */
    public Map<String, Object> getFinanceStatus() {
        List<BankFinance> bankFinances = bankFinanceRepository.findAll();
        List<FinanceStatusByYear> financeStatusByYears = new ArrayList<>();
        Map<String, Integer> bankFinanceMap = calcByYearAndBankId(bankFinances); // key == bankId/year
        bankFinanceMap.forEach((key, value) -> {
            String[] keys = key.split("/");
            Long bankId = Long.valueOf(keys[0]);
            Integer year = Integer.valueOf(keys[1]);
            System.out.println(value);


        });
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("name", "주택금융 공급현황");
        return resultMap;
    }

    private Map<String,Integer> calcByYearAndBankId(List<BankFinance> bankFinances) {
        Map<String, Integer> bankFinanceMap = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (BankFinance bankFinance : bankFinances) {
            String key = stringBuilder.append(bankFinance.getBankId())
                    .append("/")
                    .append(bankFinance.getYear())
                    .toString();
            stringBuilder.setLength(0);
            bankFinanceMap.putIfAbsent(key, 0);
//            bankFinanceMap.get(key)
            bankFinanceMap.compute(key, (s, integer) -> integer + bankFinance.getAmount());

        }
        return bankFinanceMap;
    }
}
