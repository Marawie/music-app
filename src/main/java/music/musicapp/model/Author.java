package music.musicapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameOfArtist;

    private String typeOfAuthor;
    @Size(min = 13, max = 100)
    private Integer yearOld;

    private String biography;

    @Lob
    private byte[] image;

    @OneToMany
    @JoinColumn(name = "author_genre")
    private List<Genre> genres;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_music_id")
    private Set<AlbumMusic> albumMusic = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_podcast_id")
    private Set<AlbumPodcast>  albumPodcasts= new HashSet<>();
}