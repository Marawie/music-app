package music.musicapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "album_podcast", schema = "music")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumPodcast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "album_podcast_id")
    private Set<Podcast> podcastList = new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "author_podcast_id")
    private Author authorPodcast;
}