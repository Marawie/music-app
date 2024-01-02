package music.musicapp.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "friendship", schema = "music")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private FriendshipRequestState friendshipRequestState;

    public Friendship(User friend, User user, FriendshipRequestState friendshipRequestState) {
        this.friend = friend;
        this.user = user;
        this.friendshipRequestState = friendshipRequestState;
    }
}
