package is.hi.hbv501g.verkefni.persistence.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private Integer ageRating;

    @Column(nullable = false)
    private Long duration;

    @Column(nullable = false)
    private boolean nowShowing;

}
