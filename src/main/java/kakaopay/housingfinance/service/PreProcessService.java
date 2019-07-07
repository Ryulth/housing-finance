package kakaopay.housingfinance.service;

import kakaopay.housingfinance.entity.BankFinance;
import kakaopay.housingfinance.pojo.FinanceStatusByYear;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PreProcessService {

    public Map<String, Integer> mappingAmountOfYearByBankIdAndYear(List<BankFinance> bankFinances) {
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


    public List<FinanceStatusByYear> listingFinanceStatus(Map<String, Integer> bankFinanceMap, Map<Long, String> bankNameMap) {

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
                    .detailAmount(Collections.singletonMap(bankNameMap.get(bankId), yearAmount))
                    .build();

            try {
                FinanceStatusByYear originalElement = financeStatusByYears.stream()
                        .filter(o -> o.getYear().equals(financeStatusByYear.getYear()))
                        .findFirst()
                        .orElseThrow(NoSuchElementException::new);
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
