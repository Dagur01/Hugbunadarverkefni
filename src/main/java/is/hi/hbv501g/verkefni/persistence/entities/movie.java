package is.hi.hbv501g.verkefni.persistence.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

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

    @Column(name = "movie_picture", columnDefinition = "bytea")
    private byte[] moviePicture;

    @Column(name = "movie_picture_content_type")
    private String moviePictureContentType;

}
