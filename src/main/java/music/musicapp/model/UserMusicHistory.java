package music.musicapp.model;

import jakarta.persistence.*;
import lombok.*;
import music.musicapp.model.user.User;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user_music_history", schema = "music")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMusicHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany
    @JoinColumn(name = "history_music")
    private List<Music> musics = new ArrayList<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "user_music_history")
    private User user;
}