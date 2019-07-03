package kakaopay.housingfinance.predict;

import com.github.signaflo.timeseries.TestData;
import com.github.signaflo.timeseries.TimeSeries;
import com.github.signaflo.timeseries.forecast.Forecast;
import com.github.signaflo.timeseries.model.arima.Arima;
import com.github.signaflo.timeseries.model.arima.ArimaOrder;
import kakaopay.housingfinance.entity.BankFinance;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.github.signaflo.data.visualization.Plots.plot;

@Component
public class PredictFinanceByArima implements PredictFinance{

    @Override
    public void predictFinanceByMonth(List<BankFinance> bankFinances,Integer month) {
        TimeSeries timeSeries = TestData.debitcards;
        ArimaOrder modelOrder = ArimaOrder.order(0, 1, 1, 0, 1, 1); // Note that intercept fitting will automatically be turned off
        Arima model = Arima.model(timeSeries, modelOrder);
        System.out.println(model.aic()); // Get and display the model AIC
        System.out.println(model.coefficients()); // Get and display the estimated coefficients
        System.out.println(java.util.Arrays.toString(model.stdErrors()));
        plot(model.predictionErrors());
        Forecast forecast = model.forecast(12); // To specify the alpha significance level, add it as a second argument.
        System.out.println(forecast);
    }
}
