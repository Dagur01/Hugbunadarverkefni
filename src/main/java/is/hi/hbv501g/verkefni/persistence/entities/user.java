package is.hi.hbv501g.verkefni.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import org.springframework.boot.context.properties.bind.DefaultValue;

import static org.hibernate.cfg.JdbcSettings.USER;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"passwordHash", "profilePicture", "profilePictureContentType"})
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "profile_picture", columnDefinition = "bytea")
    private byte[] profilePicture;

    @Column(name = "profile_picture_content_type")
    private String profilePictureContentType;

    @Column
    private String username;

    public enum Role {USER, ADMIN}

    @ManyToMany
    @JoinTable(
            name = "favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<movie> favoriteMovies = new HashSet<>();

    public Set<movie> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void addFavoriteMovie(movie movie) {
        favoriteMovies.add(movie);
    }

    public void removeFavoriteMovie(movie movie) {
        favoriteMovies.remove(movie);
    }




}
