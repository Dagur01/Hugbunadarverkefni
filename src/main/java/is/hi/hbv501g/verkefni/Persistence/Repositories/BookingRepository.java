package is.hi.hbv501g.verkefni.Persistence.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import is.hi.hbv501g.verkefni.Persistence.Entities.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserUsername(String username);
}
