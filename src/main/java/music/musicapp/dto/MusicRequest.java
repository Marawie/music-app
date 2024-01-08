package music.musicapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import music.musicapp.model.Genre;

import java.io.File;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MusicRequest {
    private String name;
    private File musicFile;
    private LocalDate musicAddedFromArtist;
    private Genre genre;
    private String textOfMusic;
}
