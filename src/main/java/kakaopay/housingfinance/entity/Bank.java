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
@Table(name = "bank")
@ToString
public class Bank {
    @Id
    @GeneratedValue
    @Column(name = "bank_id")
    private Long id;

    @Column(name = "bank_name")
    private String name;

}
