package investment.portfolio.investment.portfolio.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;
    @Column(unique = true)
    private String username;
    private String password;

    @Column(unique = true)

    private String email;

    @Column(unique = true)

    private String phoneNumber;

}
