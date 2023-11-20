package music.musicapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Lob
    private byte[] audio;

    @Size(min = 1, max = 50)
    private String title;

    private String textOfMusic;

    private LocalDate musicAddedFromArtist;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumMusic albumMusic;

    @ManyToOne
    @JoinColumn (name = "genre_id")
    private Genre genre;

    @ManyToMany(mappedBy = "musics")
    private Set<Playlist> playlists = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "history_music")
    private UserMusicHistory historyOfMusic;
}