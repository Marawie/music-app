package music.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import music.musicapp.model.*;

import java.util.List;

@AllArgsConstructor
@Data
public class SearchResultDto {
    private List<Music> musicList;
    private List<Podcast> podcastList;
    private List<Genre> genreList;
    private List<Author> authorList;
}