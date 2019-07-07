package kakaopay.housingfinance.converter;

import kakaopay.housingfinance.entity.Bank;
import kakaopay.housingfinance.entity.BankFinance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ObjectConverterTest {

    @InjectMocks
    ObjectConverter objectConverter;

    @Test
    public void testBankListMapper() {
        List<String> columnNames = Collections.unmodifiableList(
                Arrays.asList("연도", "월", "카카오페이", "신한은행", "기업은행"));

        List<Bank> resultList = objectConverter.bankListMapper(columnNames);

        assertThat(resultList, is(notNullValue()));
        assertThat(resultList.get(0).getName(), is(columnNames.get(2)));
        assertThat(resultList.get(1).getName(), is(columnNames.get(3)));
        assertThat(resultList.get(2).getName(), is(columnNames.get(4)));
    }

    @Test
    public void testBankFinanceListMapper() {
        List<List<String>> rowData = new ArrayList<>();
        rowData.add(Collections.unmodifiableList(
                Arrays.asList("2005","1", "2500", "1500", "1000")));

        List<Bank> banks = new ArrayList<>();
        banks.add(Bank.builder().id(1L).name("카카오페이").build());
        banks.add(Bank.builder().id(2L).name("신한은행").build());
        banks.add(Bank.builder().id(3L).name("기업은행").build());

        List<BankFinance> resultList = objectConverter.bankFinanceListMapper(rowData,banks);

        assertThat(resultList,is(notNullValue()));
        assertThat(resultList.get(0).getBankId(),is(1L));
        assertThat(resultList.get(0).getYear(),is(2005));
        assertThat(resultList.get(0).getAmount(),is(2500));
        assertThat(resultList.get(0).getMonth(),is(1));

        assertThat(resultList.get(1).getBankId(),is(2L));
        assertThat(resultList.get(1).getYear(),is(2005));
        assertThat(resultList.get(1).getAmount(),is(1500));
        assertThat(resultList.get(1).getMonth(),is(1));

        assertThat(resultList.get(2).getBankId(),is(3L));
        assertThat(resultList.get(2).getYear(),is(2005));
        assertThat(resultList.get(2).getAmount(),is(1000));
        assertThat(resultList.get(2).getMonth(),is(1));
    }
}