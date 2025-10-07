package is.hi.hbv501g.verkefni.services;

import is.hi.hbv501g.verkefni.persistence.entities.Booking;

import java.util.List;

public interface BookingService {
    Booking create(String username, Long screeningId, List<Long> seatIds);

    Booking confirm(Long movieId, Long screeningId, Long seatId, String movieHallName);

    Booking cancel(Long bookingId);

    Booking get(Long bookingId);

    List<Booking> listForUser(String username);

    Booking addSeat(Long bookingId, Long seatId);

    Booking removeSeat(Long bookingId, Long seatId);

    boolean isSeatAvailable(Long screeningId, Long seatId);

    String seatStatus(boolean isAvailable, boolean isHeld, boolean isReserved);

    Booking addScreening(Long bookingId, Long screeningId);

    Booking removeScreening(Long bookingId, Long screeningId);
}
