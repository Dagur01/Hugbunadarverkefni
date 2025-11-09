package is.hi.hbv501g.verkefni.persistence.repositories;

import is.hi.hbv501g.verkefni.persistence.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByNowShowingTrue();
    List<Movie> findByGenre(String genre);
    Optional<Movie> findById(Long movieId);

}
