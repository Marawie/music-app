package music.musicapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "podcast", schema = "music")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Podcast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @NotBlank
    @Lob
    private byte[] podcast;

    private LocalDate podcastAddedByArtist;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany
    private Set<Author> author = new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "album_podcast_id")
    private AlbumPodcast albumPodcast;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "history_podcast")
    private UserPodcastHistory historyOfPodcast;
}