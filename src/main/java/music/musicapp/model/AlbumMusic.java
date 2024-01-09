package music.musicapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "album_music", schema = "music")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumMusic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long albumID;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "album_id")
    private Set<Music> musicList = new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}