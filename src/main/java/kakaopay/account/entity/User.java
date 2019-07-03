package kakaopay.account.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "user")
@ToString
public class User {
    protected User() {
    }

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;
}
