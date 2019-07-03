package kakaopay.housingfinance.predict;

import kakaopay.housingfinance.entity.BankFinance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PredictFinance {
    Integer predictFinanceAmount(List<BankFinance> bankFinances,Integer predictYear ,Integer predictMonth);
}
