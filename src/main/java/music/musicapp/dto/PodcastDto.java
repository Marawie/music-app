package music.musicapp.dto;

import music.musicapp.model.AlbumPodcast;
import music.musicapp.model.Author;
import music.musicapp.model.UserPodcastHistory;

import java.time.LocalDate;
import java.util.Set;

public class PodcastDto {

    private Long id;
    private String name;

    private byte[] podcast;

    private LocalDate podcastAddedByArtist;

    private Set<Author> author;

    private AlbumPodcast albumPodcast;

    private UserPodcastHistory historyOfPodcast;
}
