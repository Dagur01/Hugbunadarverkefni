package is.hi.hbv501g.verkefni.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"passwordHash", "profilePicture", "profilePictureContentType"})
public class User {

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
    private Set<Movie> favoriteMovies = new HashSet<>();

    public Set<Movie> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void addFavoriteMovie(Movie movie) {
        favoriteMovies.add(movie);
    }

    public void removeFavoriteMovie(Movie movie) {
        favoriteMovies.remove(movie);
    }


}
