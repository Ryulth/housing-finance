package kakaopay.housingfinance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor()
@Table(name = "supply-status")
public class SupplyStatus {
    @Id
    @GeneratedValue
    @Column(name = "supply-status-id")
    private Long id;
    
    @Column(name = "bank-id")
    private Long bankId;

    @Column
    private Integer year;

    @Column
    private Integer month;

    @Column
    private Integer amount;
}
