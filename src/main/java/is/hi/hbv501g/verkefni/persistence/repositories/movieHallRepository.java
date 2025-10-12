package is.hi.hbv501g.verkefni.persistence.repositories;

import is.hi.hbv501g.verkefni.persistence.entities.movieHall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface movieHallRepository extends JpaRepository<movieHall, Long> {
    List<movieHall> findByNowShowingTrue();
    List<movieHall> findByName(String name);
}
