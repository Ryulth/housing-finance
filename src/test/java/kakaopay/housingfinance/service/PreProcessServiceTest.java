package kakaopay.housingfinance.service;

import kakaopay.housingfinance.entity.BankFinance;
import kakaopay.housingfinance.pojo.FinanceStatusByYear;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

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

    @Test
    public void testListingFinanceStatus(){
        Map<String,Integer> bankFinanceMap = new HashMap<>();
        bankFinanceMap.put("1/2015",2000);
        bankFinanceMap.put("1/2016",4000);

        Map<Long, String> bankNameMap = new HashMap<>();
        bankNameMap.put(1L,"카카오페이");

        List<FinanceStatusByYear> financeStatusByYears = preProcessService.listingFinanceStatus(bankFinanceMap,bankNameMap);

        FinanceStatusByYear firstFinanceStatusByYear = FinanceStatusByYear.builder()
                .year("2015 년")
                .totalAmount(2000)
                .detailAmount(Collections.singletonMap("카카오페이",2000))
                .build();
        FinanceStatusByYear secondFinanceStatusByYear = FinanceStatusByYear.builder()
                .year("2016 년")
                .totalAmount(4000)
                .detailAmount(Collections.singletonMap("카카오페이",4000))
                .build();

        assertThat(financeStatusByYears.get(0),is(firstFinanceStatusByYear));
        assertThat(financeStatusByYears.get(1),is(secondFinanceStatusByYear));
    }

}