package kakaopay.housingfinance.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class FinanceStatusByYear implements Comparable<FinanceStatusByYear>{
    private String year;
    @JsonProperty("total_amount")
    private Integer totalAmount;
    @JsonProperty("detail_amount")
    private Map<String,Integer> detailAmount;

    @JsonIgnore
    public void addTotalAmount(Integer yearAmount){
        this.totalAmount += yearAmount;
    }

    @JsonIgnore
    public void putDetailAmount(Map<String,Integer> detailAmount){
        Map<String,Integer> mergeMap = new HashMap<>();
        mergeMap.putAll(this.detailAmount);
        mergeMap.putAll(detailAmount);
        this.detailAmount = mergeMap;
    }

    @Override
    public int compareTo(FinanceStatusByYear financeStatusByYear) {
        return this.year.compareTo(financeStatusByYear.getYear());
    }
}
