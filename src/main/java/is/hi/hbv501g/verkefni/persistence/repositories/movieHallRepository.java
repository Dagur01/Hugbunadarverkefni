package is.hi.hbv501g.verkefni.persistence.repositories;

import is.hi.hbv501g.verkefni.persistence.entities.MovieHall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieHallRepository extends JpaRepository<MovieHall, Long> {
    List<MovieHall> findByNowShowingTrue();
    List<MovieHall> findByName(String name);
}
