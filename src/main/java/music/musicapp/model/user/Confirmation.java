package music.musicapp.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import music.musicapp.model.token.Token;

import java.time.LocalDateTime;

@Data

@Entity
@Table(name = "Confirmation", schema = "music")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Confirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private LocalDateTime localDateTime;
    private ConfirmationState confirmationState;

    @OneToOne(targetEntity = Token.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "token_id")
    private Token token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public Confirmation(Token token, User user, ConfirmationState confirmationState) {
        this.localDateTime = LocalDateTime.now();
        this.token = token;
        this.user = user;
        this.confirmationState = confirmationState;
    }
}
