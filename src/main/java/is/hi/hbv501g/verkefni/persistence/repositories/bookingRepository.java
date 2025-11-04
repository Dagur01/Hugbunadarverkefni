package is.hi.hbv501g.verkefni.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.hbv501g.verkefni.persistence.entities.Booking;
import is.hi.hbv501g.verkefni.persistence.entities.Seat;
import is.hi.hbv501g.verkefni.persistence.entities.User;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsBySeat(Seat seat);
    List<Booking> findAllByUser(User user);
}
