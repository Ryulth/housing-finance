package kakaopay.housingfinance.service;

import kakaopay.housingfinance.entity.Bank;
import kakaopay.housingfinance.entity.BankFinance;
import kakaopay.housingfinance.pojo.FinanceStatusByYear;
import kakaopay.housingfinance.repository.BankFinanceRepository;
import kakaopay.housingfinance.repository.BankRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FinanceService {
    private final BankRepository bankRepository;
    private final BankFinanceRepository bankFinanceRepository;

    public FinanceService(BankRepository bankRepository, BankFinanceRepository bankFinanceRepository) {
        this.bankRepository = bankRepository;
        this.bankFinanceRepository = bankFinanceRepository;
    }
    //TODO 메소드 구조 변경 요망
    public Map<String, Object> getHighestBank() {
        List<BankFinance> bankFinances = bankFinanceRepository.findAll();
        Map<String, Integer> bankFinanceMap = calcYearAmountByBankId(bankFinances); // key == bankId/year

        String key = Collections.max(bankFinanceMap.entrySet(),
                Comparator.comparingInt(Map.Entry::getValue)).getKey();

        String[] keys = key.split("/");
        Long bankId = Long.valueOf(keys[0]);
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(EntityNotFoundException::new);

        Integer year = Integer.valueOf(keys[1]);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("year", year);
        resultMap.put("bank", bank.getName());
        return resultMap;
    }

    //TODO 이름 매퍼 매핑과 관심사 분리 요망
    public Map<String, Object> getFinanceStatus() {
        List<BankFinance> bankFinances = bankFinanceRepository.findAll();
        Map<String, Integer> bankFinanceMap = calcYearAmountByBankId(bankFinances); // key == bankId/year

        List<FinanceStatusByYear> financeStatusByYears = mappingFinanceStatus(bankFinanceMap);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("name", "주택금융 공급현황");
        resultMap.put("status", financeStatusByYears);
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

    private List<FinanceStatusByYear> mappingFinanceStatus(Map<String, Integer> bankFinanceMap) {
        Map<Long, String> bankMap = bankRepository.findAll()
                .stream().collect(Collectors.toMap(Bank::getId, Bank::getName));

        List<FinanceStatusByYear> financeStatusByYears = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        bankFinanceMap.forEach((key, yearAmount) -> {
            String[] keys = key.split("/");
            Long bankId = Long.valueOf(keys[0]);
            String year = stringBuilder.append(keys[1])
                    .append(" 년")
                    .toString();
            stringBuilder.setLength(0);

            FinanceStatusByYear financeStatusByYear = FinanceStatusByYear.builder()
                    .year(year)
                    .totalAmount(yearAmount)
                    .detailAmount(Collections.singletonMap(bankMap.get(bankId), yearAmount))
                    .build();

            try {
                FinanceStatusByYear originalElement = financeStatusByYears.stream()
                        .filter(o -> o.equals(financeStatusByYear))
                        .findFirst().orElseThrow(NoSuchElementException::new);
                originalElement.addTotalAmount(yearAmount);
                originalElement.putDetailAmount(financeStatusByYear.getDetailAmount());
            } catch (NoSuchElementException e) {
                financeStatusByYears.add(financeStatusByYear);
            }
        });
        Collections.sort(financeStatusByYears);

        return financeStatusByYears;
    }
}
