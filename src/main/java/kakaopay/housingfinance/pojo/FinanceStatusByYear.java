package kakaopay.housingfinance.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class FinanceStatusByYear {
    private String year;
    @JsonProperty("total_amount")
    private Integer totalAmount;
    @JsonProperty("detail_amount")
    private Map<String,Integer> detailAmount;
}
