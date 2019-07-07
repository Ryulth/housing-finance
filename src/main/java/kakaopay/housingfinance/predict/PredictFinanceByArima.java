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
import java.util.ArrayList;
import java.util.List;

@Component
public class PredictFinanceByArima implements PredictFinance {

    @Override
    public int predictFinanceAmount(List<BankFinance> bankFinances, int predictYear, int predictMonth) {
        TimePeriod timePeriodMonth = TimePeriod.oneMonth();
        TimeSeries timeSeries = TimeSeries.from(
                timePeriodMonth,
                preprocessOffsetDateTimes(bankFinances),
                preprocessData(bankFinances));

        ArimaOrder modelOrder = ArimaOrder.order(0, 0, 0, 1, 1, 1); // Note that intercept fitting will automatically be turned off
        Arima model = Arima.model(timeSeries, modelOrder);

        BankFinance lastBankFinance = bankFinances.get(bankFinances.size() - 1);
        Forecast forecast = model.forecast(12 + (12 - lastBankFinance.getMonth())); // 데이터가 12월 채워져있지 않을 경우 고려

        OffsetDateTime predictOffsetDateTime = OffsetDateTime.of(
                LocalDateTime.of(predictYear, predictMonth, 01, 00, 00),
                ZoneOffset.ofHoursMinutes(00, 00));

        return (int) forecast.pointEstimates().at(predictOffsetDateTime);
    }

    private List<OffsetDateTime> preprocessOffsetDateTimes(List<BankFinance> bankFinances) {
        List<OffsetDateTime> offsetDateTimes = new ArrayList<>();
        for (BankFinance bankFinance : bankFinances) {
            offsetDateTimes.add(OffsetDateTime.of(
                    LocalDateTime.of(bankFinance.getYear(), bankFinance.getMonth(), 01, 00, 00),
                    ZoneOffset.ofHoursMinutes(00, 00)));
        }
        return offsetDateTimes;
    }

    private double[] preprocessData(List<BankFinance> bankFinances) {
        double[] values = new double[bankFinances.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = bankFinances.get(i).getAmount().doubleValue();
        }
        return values;
    }


}
