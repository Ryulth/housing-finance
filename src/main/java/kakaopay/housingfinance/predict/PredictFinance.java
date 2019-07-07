package kakaopay.housingfinance.predict;

import kakaopay.housingfinance.entity.BankFinance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PredictFinance {
    int predictFinanceAmount(List<BankFinance> bankFinances,int predictYear ,int predictMonth);
}
