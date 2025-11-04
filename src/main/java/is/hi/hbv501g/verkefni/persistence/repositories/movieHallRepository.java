package is.hi.hbv501g.verkefni.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.hbv501g.verkefni.persistence.entities.MovieHall;

import java.util.List;

public interface MovieHallRepository extends JpaRepository<MovieHall, Long> {
    List<MovieHall> findByNowShowingTrue();
    List<MovieHall> findByName(String name);
}
