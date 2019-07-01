package kakaopay.housingfinance.converter;

import kakaopay.housingfinance.entity.Bank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ObjectConverter {
    private static final List<String> removeColumn = Collections.unmodifiableList(
            Arrays.asList("연도", "월"));

    //TODO removeColumn String Equal 후 삭제
    public List<Bank> bankMapper(List<String> columnList) {
        List<Bank> banks = new ArrayList<>();
        for (int i = removeColumn.size(); i < columnList.size(); i++) {
            banks.add(Bank.builder()
                    .name(columnList.get(i))
                    .build());
        }
        return banks;
    }
}
