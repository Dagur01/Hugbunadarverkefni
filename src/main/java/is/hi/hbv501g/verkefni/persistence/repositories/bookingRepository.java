package is.hi.hbv501g.verkefni.persistence.repositories;
import is.hi.hbv501g.verkefni.persistence.entities.booking;
import is.hi.hbv501g.verkefni.persistence.entities.seat;
import is.hi.hbv501g.verkefni.persistence.entities.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface bookingRepository extends JpaRepository<booking, Long> {
    boolean existsBySeat(seat seat);
    List<booking> findAllByUser(user user);
}