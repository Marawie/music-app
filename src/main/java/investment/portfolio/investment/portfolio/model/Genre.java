package investment.portfolio.investment.portfolio.model;

import jakarta.persistence.*;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String[] genres;

    @OneToMany
    @JoinColumn(name = "genre_id")
    private Music music;
}
