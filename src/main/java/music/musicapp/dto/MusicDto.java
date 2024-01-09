package music.musicapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import music.musicapp.model.Genre;

import java.time.LocalDate;
@Builder
@Getter
@Setter
public class MusicDto {
    private String name;
    private byte[] musicFile;
    private LocalDate musicAddedFromArtist;
    private Genre genre;
    private String textOfMusic;
    
}
