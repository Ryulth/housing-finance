package kakaopay.housingfinance.service;

import kakaopay.housingfinance.entity.BankFinance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PreProcessServiceTest {

    @InjectMocks
    PreProcessService preProcessService;

    @Test
    public void testMappingAmountOfYearByBankIdAndYear() {
        List<BankFinance> bankFinances = new ArrayList<>();
        bankFinances.add(BankFinance.builder()
                .bankId(1L).year(2015).month(1).amount(1000).build());
        bankFinances.add(BankFinance.builder()
                .bankId(1L).year(2015).month(2).amount(1000).build());
        bankFinances.add(BankFinance.builder()
                .bankId(1L).year(2016).month(1).amount(2000).build());
        bankFinances.add(BankFinance.builder()
                .bankId(1L).year(2016).month(2).amount(2000).build());

        Map<String, Integer> bankFinanceMap = preProcessService.mappingAmountOfYearByBankIdAndYear(bankFinances);

        assertThat(bankFinanceMap.get("1/2015"),is(2000));
        assertThat(bankFinanceMap.get("1/2016"),is(4000));
    }

}