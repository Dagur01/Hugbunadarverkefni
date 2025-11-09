package is.hi.hbv501g.verkefni.persistence.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.hbv501g.verkefni.persistence.entities.*;


public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUser(User user);
    boolean existsBySeatAndScreening(Seat seat, Screening screening); // better than existsBySeat
}

