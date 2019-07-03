package kakaopay.housingfinance.service;

import kakaopay.housingfinance.entity.Bank;
import kakaopay.housingfinance.entity.BankFinance;
import kakaopay.housingfinance.pojo.FinanceStatusByYear;
import kakaopay.housingfinance.predict.PredictFinance;
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
    private final PreProcessService preProcessService;
    private final PredictFinance predictFinance;

    public FinanceService(BankRepository bankRepository, BankFinanceRepository bankFinanceRepository, PreProcessService preProcessService, PredictFinance predictFinance) {
        this.bankRepository = bankRepository;
        this.bankFinanceRepository = bankFinanceRepository;
        this.preProcessService = preProcessService;
        this.predictFinance = predictFinance;
    }

    public Map<String, Object> getAllFinances() {
        List<BankFinance> bankFinances = bankFinanceRepository.findAll();
        Map<String, Integer> bankFinanceMap = preProcessService.mappingAmountOfYearByBankIdAndYear(bankFinances); // key == bankId/year
        Map<Long, String> bankNameMap = bankRepository.findAll()
                .stream().collect(Collectors.toMap(Bank::getId, Bank::getName));
        List<FinanceStatusByYear> financeStatusByYears = preProcessService.listingFinanceStatus(bankFinanceMap,bankNameMap);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("name", "주택금융 공급현황");
        resultMap.put("status", financeStatusByYears);
        return resultMap;
    }

    public Map<String, Object> getHighestYearBank() {
        List<BankFinance> bankFinances = bankFinanceRepository.findAll();
        Map<String, Integer> bankFinanceMap = preProcessService.mappingAmountOfYearByBankIdAndYear(bankFinances); // key == bankId/year

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

    public Map<String, Object> getMinMaxYearBank(String bankName) {
        Bank bank = bankRepository.findByName(bankName)
                .orElseThrow(EntityNotFoundException::new);
        List<BankFinance> bankFinances = bankFinanceRepository.findAllByBankId(bank.getId());

        Map<String, Integer> bankFinanceMap = preProcessService.mappingAmountOfYearByBankIdAndYear(bankFinances); // key == bankId/year
        Map.Entry<String, Integer> min = Collections.min(bankFinanceMap.entrySet(),
                Comparator.comparing(Map.Entry::getValue));
        Map.Entry<String, Integer> max = Collections.max(bankFinanceMap.entrySet(),
                Comparator.comparing(Map.Entry::getValue));

        List<Map<String, Object>> supportAmounts = new ArrayList<>();
        supportAmounts.add(getSupportAmountMap(min));
        supportAmounts.add(getSupportAmountMap(max));

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("bank", bankName);
        resultMap.put("support_amount", supportAmounts);
        return resultMap;
    }

    private Map<String, Object> getSupportAmountMap(Map.Entry<String, Integer> entry) {
        Integer year = Integer.valueOf(entry.getKey().split("/")[1]);

        Map<String, Object> supportAmountMap = new HashMap<>();
        supportAmountMap.put("year", year);
        supportAmountMap.put("amount", entry.getValue() / 12);
        return supportAmountMap;
    }

    public Map<String,Object> getPredictAmountByMonth(String bankName,Integer month){
        Bank bank = bankRepository.findByName(bankName)
                .orElseThrow(EntityNotFoundException::new);
        List<BankFinance> bankFinances = bankFinanceRepository.findAllByBankId(bank.getId());
        predictFinance.predictFinanceByMonth(bankFinances,month);
        Integer predictAmount = 100;


        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("bank",bankName);
        resultMap.put("year",2018);
        resultMap.put("month",month);
        resultMap.put("amount",predictAmount);
        return resultMap;
    }
}
