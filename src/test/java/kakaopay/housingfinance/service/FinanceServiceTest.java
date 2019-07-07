package kakaopay.housingfinance.service;

import kakaopay.housingfinance.entity.Bank;
import kakaopay.housingfinance.entity.BankFinance;
import kakaopay.housingfinance.pojo.FinanceStatusByYear;
import kakaopay.housingfinance.predict.PredictFinance;
import kakaopay.housingfinance.repository.BankFinanceRepository;
import kakaopay.housingfinance.repository.BankRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class FinanceServiceTest {

    @InjectMocks
    FinanceService financeService;

    @Mock
    BankRepository bankRepository;
    @Mock
    BankFinanceRepository bankFinanceRepository;
    @Spy
    PreProcessService preProcessService;
    @Spy
    PredictFinance predictFinance;

    @Before
    public void setUp() {
        mockingRepository();
    }


    @Test
    public void testGetAllFinances() {
        Map<String, Integer> detailAmountMap2015 = new HashMap<>();
        detailAmountMap2015.put("카카오페이", 156000);
        detailAmountMap2015.put("신한은행", 117000);

        Map<String, Integer> detailAmountMap2016 = new HashMap<>();
        detailAmountMap2016.put("카카오페이", 78000);
        detailAmountMap2016.put("신한은행", 39000);

        List<FinanceStatusByYear> financeStatusByYears = new ArrayList<>();
        financeStatusByYears.add(FinanceStatusByYear.builder()
                .year("2015 년")
                .totalAmount(273000)
                .detailAmount(detailAmountMap2015)
                .build());
        financeStatusByYears.add(FinanceStatusByYear.builder()
                .year("2016 년")
                .totalAmount(117000)
                .detailAmount(detailAmountMap2016)
                .build());

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("name", "주택금융 공급현황");
        resultMap.put("status", financeStatusByYears);
        assertThat(resultMap, is(financeService.getAllFinances()));
    }

    @Test
    public void testGetHighestYearBank() {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("year", "2015");
        resultMap.put("bank", "카카오페이");
        assertThat(resultMap, is(financeService.getHighestYearBank()));
    }

    @Test
    public void testGetMinMaxYearBank() {
        Map<String, Object> supportAmountMapMin = new HashMap<>();
        supportAmountMapMin.put("year", "2016");
        supportAmountMapMin.put("amount", 6500);

        Map<String, Object> supportAmountMapMax = new HashMap<>();
        supportAmountMapMax.put("year", "2015");
        supportAmountMapMax.put("amount", 13000);

        List<Map<String, Object>> supportAmounts = new ArrayList<>();
        supportAmounts.add(supportAmountMapMin);
        supportAmounts.add(supportAmountMapMax);

        String bankName = "카카오페이";
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("bank", bankName);
        resultMap.put("support_amount", supportAmounts);

        assertThat(resultMap, is(financeService.getMinMaxYearBank(bankName)));
    }

    @Test
    public void testGetPredictAmountByMonth() {
        String bankName = "카카오페이";
        int predictMonth = 12;

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("bank", bankName);
        resultMap.put("year", 2017);
        resultMap.put("month", predictMonth);
        resultMap.put("amount", 0);
        assertThat(resultMap, is(financeService.getPredictAmountByMonth(bankName, predictMonth)));
    }

    private void mockingRepository() {
        List<Bank> banks = new ArrayList<>();
        banks.add(Bank.builder().id(1L).name("카카오페이").build());
        banks.add(Bank.builder().id(2L).name("신한은행").build());
        when(bankRepository.findAll()).thenReturn(banks);

        when(bankRepository.findById(1L)).thenReturn(Optional.of(
                Bank.builder().id(1L).name("카카오페이").build()));

        when(bankRepository.findByName("카카오페이")).thenReturn(Optional.of(
                Bank.builder().id(1L).name("카카오페이").build()));

        List<BankFinance> bankFinances = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            bankFinances.add(BankFinance.builder().bankId(1L).amount(2000 * i).year(2015).month(i).build());
            bankFinances.add(BankFinance.builder().bankId(2L).amount(1500 * i).year(2015).month(i).build());
            bankFinances.add(BankFinance.builder().bankId(1L).amount(1000 * i).year(2016).month(i).build());
            bankFinances.add(BankFinance.builder().bankId(2L).amount(500 * i).year(2016).month(i).build());
        }
        when(bankFinanceRepository.findAll()).thenReturn(bankFinances);
        when(bankFinanceRepository.findAllByBankId(1L)).thenReturn(
                bankFinances.stream()
                        .filter(bankFinance -> bankFinance.getBankId() == 1L)
                        .collect(Collectors.toList()));

    }

}