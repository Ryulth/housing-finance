package kakaopay.housingfinance.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "bank")
@ToString
public class Bank {
    protected Bank(){}

    @Id
    @GeneratedValue
    @Column(name = "bank_id")
    private Long id;

    @Column(name = "bank_name")
    private String name;

}
