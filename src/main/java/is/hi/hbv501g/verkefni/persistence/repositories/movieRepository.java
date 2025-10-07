package is.hi.hbv501g.verkefni.persistence.repositories;

import is.hi.hbv501g.verkefni.persistence.entities.movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface movieRepository extends JpaRepository<movie, Long> {
    List<movie> findByNowShowingTrue();
    List<movie> findByGenre(String genre);
}
