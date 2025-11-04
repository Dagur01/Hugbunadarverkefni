package is.hi.hbv501g.verkefni.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.hbv501g.verkefni.persistence.entities.MovieHall;
import is.hi.hbv501g.verkefni.persistence.entities.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findAllByMovieHall(MovieHall movieHall);
}


