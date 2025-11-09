package is.hi.hbv501g.verkefni.persistence.repositories;

import is.hi.hbv501g.verkefni.persistence.entities.MovieHall;
import is.hi.hbv501g.verkefni.persistence.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findAllByMovieHall(MovieHall movieHall);
}


