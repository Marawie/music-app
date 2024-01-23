package music.musicapp.model.user;

import jakarta.persistence.*;
import lombok.*;

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
    @Enumerated(EnumType.STRING)
    @Column(name = "confirmation_state")
    private ConfirmationState confirmationState;
    private String token;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "confirmation")
    @JoinColumn(nullable = false, name = "user_confirmation")
    private User user;

    public Confirmation(LocalDateTime localDateTime, ConfirmationState confirmationState, String token, User user) {
        this.localDateTime = localDateTime;
        this.confirmationState = confirmationState;
        this.token = token;
        this.user = user;
    }
}