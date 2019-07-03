package kakaopay.housingfinance.predict;

import com.github.signaflo.timeseries.TimePeriod;
import com.github.signaflo.timeseries.TimeSeries;
import com.github.signaflo.timeseries.forecast.Forecast;
import com.github.signaflo.timeseries.model.arima.Arima;
import com.github.signaflo.timeseries.model.arima.ArimaOrder;
import kakaopay.housingfinance.entity.BankFinance;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
public class PredictFinanceByArima implements PredictFinance {

    @Override
    public void predictFinanceByMonth(List<BankFinance> bankFinances, Integer month) {

        OffsetDateTime offsetDateTime = OffsetDateTime.of(
                LocalDateTime.of(bankFinances.get(0).getYear(), bankFinances.get(0).getMonth(), 01, 00, 00),
                ZoneOffset.ofHoursMinutes(00, 00));
        TimePeriod timePeriodMonth = TimePeriod.oneMonth();
        TimeSeries timeSeries = TimeSeries.from(timePeriodMonth,offsetDateTime, preprocessData(bankFinances));
        ArimaOrder modelOrder = ArimaOrder.order(0, 1, 1, 0, 1, 1); // Note that intercept fitting will automatically be turned off
        Arima model = Arima.model(timeSeries, modelOrder);
        Forecast forecast = model.forecast(12); // To specify the alpha significance level, add it as a second argument.

        System.out.println(forecast);


        System.out.println(offsetDateTime);

    }
    
    private double[] preprocessData(List<BankFinance> bankFinances) {
        double[] values = new double[bankFinances.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = bankFinances.get(i).getAmount().doubleValue();
        }
        return values;
    }

}
