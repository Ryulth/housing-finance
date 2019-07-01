package kakaopay.housingfinance.converter;

import kakaopay.housingfinance.entity.Bank;
import kakaopay.housingfinance.entity.SupplyStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ObjectConverter {
    private static final List<String> removeColumns = Collections.unmodifiableList(
            Arrays.asList("연도", "월"));

    //TODO removeColumn String Equal 후 삭제
    public List<Bank> bankListMapper(List<String> columnNames) {
        List<Bank> banks = new ArrayList<>();
        for (int i = removeColumns.size(); i < columnNames.size(); i++) {
            banks.add(
                    Bank.builder()
                            .name(columnNames.get(i))
                            .build());
        }
        return banks;
    }

    //TODO magic number 삭제고안
    public List<SupplyStatus> supplyStatusListMapper(List<List<String>> rowData, List<Bank> banks) {
        List<SupplyStatus> supplyStatuses = new ArrayList<>();

        for (List<String> row : rowData) {
            Integer year = Integer.valueOf(row.get(0));
            Integer month = Integer.valueOf(row.get(1));
            for (int i = removeColumns.size(); i < row.size(); i++) {
                supplyStatuses.add(
                        SupplyStatus.builder()
                                .year(year)
                                .month(month)
                                .bankId(banks.get(i - removeColumns.size()).getId())
                                .amount(Integer.valueOf(row.get(i).replaceAll(",", "")))
                                .build());
            }
        }
        return supplyStatuses;
    }
}
