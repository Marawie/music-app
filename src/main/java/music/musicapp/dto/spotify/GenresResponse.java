package music.musicapp.dto.spotify;


import java.util.Arrays;

public record GenresResponse(String[] genres) {
    @Override
    public String toString() {
        return Arrays.toString(genres);
    }
}


