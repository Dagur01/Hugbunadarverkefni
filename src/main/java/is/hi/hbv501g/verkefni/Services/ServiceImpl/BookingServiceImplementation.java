package is.hi.hbv501g.verkefni.Services.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import is.hi.hbv501g.verkefni.Persistence.Entities.Booking;
import is.hi.hbv501g.verkefni.Services.BookingService;

@Service
public class BookingServiceImplementation implements BookingService {

    @Override
    public Booking create(String username, Long screeningId, List<Long> seatIds) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Booking confirm(Long movieId, Long screeningId, Long seatId, String movieHallName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'confirm'");
    }

    @Override
    public Booking cancel(Long bookingId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancel'");
    }

    @Override
    public Booking get(Long bookingId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Booking> listForUser(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listForUser'");
    }

    @Override
    public Booking addSeat(Long bookingId, Long seatId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addSeat'");
    }

    @Override
    public Booking removeSeat(Long bookingId, Long seatId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeSeat'");
    }

    @Override
    public boolean isSeatAvailable(Long screeningId, Long seatId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isSeatAvailable'");
    }

    @Override
    public String seatStatus(boolean isAvailable, boolean isHeld, boolean isReserved) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'seatStatus'");
    }

    @Override
    public Booking addScreening(Long bookingId, Long screeningId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addScreening'");
    }

    @Override
    public Booking removeScreening(Long bookingId, Long screeningId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeScreening'");
    }

}
