package kakaopay.housingfinance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor()
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue
    @Column(name = "bank_id")
    private Long id;

    @Column(name = "bank_name")
    private String name;

    @Column
    private Integer year;
}
