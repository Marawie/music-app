package music.musicapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import music.musicapp.model.user.User;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPodcastHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "history_podcast")
    private List<Podcast> podcasts = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_podcast_history")
    private User user;
}