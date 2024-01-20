package music.musicapp.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import static music.musicapp.model.user.ConfirmationState.EMAIL_VERIFICATION_NOT_ACCEPTED;

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
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public Confirmation(String token, User user) {
        this.localDateTime = LocalDateTime.now();
        this.token = token;
        this.user = user;
        this.confirmationState = EMAIL_VERIFICATION_NOT_ACCEPTED;
    }
}
