package music.musicapp.model;

import lombok.*;
import music.musicapp.model.user.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "playlist", schema = "music")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean isPrivate;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "playlist_music", joinColumns =
    @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "music_id")
    )
    private Set<Music> musics = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "playlist_podcast", joinColumns =
    @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "podcast_id")
    )
    private Set<Podcast> podcasts = new HashSet<>();
}