package kakaopay.housingfinance.predict;

import kakaopay.housingfinance.entity.BankFinance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PredictFinanceByArimaTest {

    @InjectMocks
    PredictFinanceByArima predictFinanceByArima;

    @Test
    public void testPredictFinanceAmount() {
        List<BankFinance> bankFinances = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            bankFinances.add(BankFinance.builder()
                    .bankId(1L).year(2015).month(i+1).amount(1000).build());
            bankFinances.add(BankFinance.builder()
                    .bankId(1L).year(2016).month(i+1).amount(1000).build());
        }
        int predictAmount =predictFinanceByArima.predictFinanceAmount(bankFinances,2017,1);

        assertThat(predictAmount,is(notNullValue()));
    }

}