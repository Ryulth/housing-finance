package kakaopay.housingfinance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor()
@Table(name = "supply_status")
@ToString
public class SupplyStatus {
    protected SupplyStatus(){}

    @Id
    @GeneratedValue
    @Column(name = "supply_status_id")
    private Long id;

    @Column(name = "bank_id")
    private Long bankId;

    @Column
    private Integer year;

    @Column
    private Integer month;

    @Column
    private Integer amount;
}
