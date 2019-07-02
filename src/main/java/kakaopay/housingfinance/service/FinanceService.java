package kakaopay.housingfinance.service;

import kakaopay.housingfinance.entity.BankFinance;
import kakaopay.housingfinance.pojo.FinanceStatusByYear;
import kakaopay.housingfinance.repository.BankFinanceRepository;
import kakaopay.housingfinance.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FinanceService {
    private final BankRepository bankRepository;
    private final BankFinanceRepository bankFinanceRepository;

    public FinanceService(BankRepository bankRepository, BankFinanceRepository bankFinanceRepository) {
        this.bankRepository = bankRepository;
        this.bankFinanceRepository = bankFinanceRepository;
    }

    public Map<String, Object> getFinanceStatus() {
        List<BankFinance> bankFinances = bankFinanceRepository.findAll();
        List<FinanceStatusByYear> financeStatusByYears = new ArrayList<>();
        Map<String, Integer> bankFinanceMap = calcYearAmountByBankId(bankFinances); // key == bankId/year

        bankFinanceMap.forEach((key, yearAmount) -> {
            String[] keys = key.split("/");
            Long bankId = Long.valueOf(keys[0]);
            Integer year = Integer.valueOf(keys[1]);
            FinanceStatusByYear financeStatusByYear = FinanceStatusByYear.builder()
                    .year(year.toString())
                    .totalAmount(yearAmount)
                    .detailAmount(Collections.singletonMap(bankId.toString(),yearAmount))
                    .build();
            if(financeStatusByYears.contains(financeStatusByYear)){
                FinanceStatusByYear originalElement = financeStatusByYears.stream()
                        .filter(o -> o.equals(financeStatusByYear))
                        .findFirst().orElseThrow(NoSuchElementException::new);
                originalElement.putDetailAmount(financeStatusByYear.getDetailAmount());
            }
            else {
                financeStatusByYears.add(financeStatusByYear);
            }
        });
        System.out.println(financeStatusByYears);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("name", "주택금융 공급현황");
        return resultMap;
    }

    private Map<String, Integer> calcYearAmountByBankId(List<BankFinance> bankFinances) {
        Map<String, Integer> bankFinanceMap = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (BankFinance bankFinance : bankFinances) {
            String key = stringBuilder.append(bankFinance.getBankId())
                    .append("/")
                    .append(bankFinance.getYear())
                    .toString();
            stringBuilder.setLength(0);
            bankFinanceMap.putIfAbsent(key, 0);
            bankFinanceMap.compute(key, (s, integer) -> integer + bankFinance.getAmount());
        }
        return bankFinanceMap;
    }
}
