package is.hi.hbv501g.verkefni.persistence.repositories;

import is.hi.hbv501g.verkefni.persistence.entities.movieHall;
import is.hi.hbv501g.verkefni.persistence.entities.seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface seatRepository extends JpaRepository<seat, Long> {
    List<seat> findAllByMovieHall(movieHall movieHall);
    boolean existsBySeatId(seat seat);
}


