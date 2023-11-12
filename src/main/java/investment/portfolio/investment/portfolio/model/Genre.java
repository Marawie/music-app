package investment.portfolio.investment.portfolio.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
@Data
@Entity
@Builder
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String[] genres;

    @OneToMany
    @JoinColumn(name = "genre_id")
    private Set<Music> music;
}