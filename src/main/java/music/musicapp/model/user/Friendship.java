package music.musicapp.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;
}
