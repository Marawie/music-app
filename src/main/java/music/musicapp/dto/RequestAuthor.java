package music.musicapp.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestAuthor {
    private String nameOfArtist;
    private Integer yearOld;
    private String biography;
}
