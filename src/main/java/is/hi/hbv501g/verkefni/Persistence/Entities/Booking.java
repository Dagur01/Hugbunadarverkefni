package is.hi.hbv501g.verkefni.Persistence.Entities;
import java.util.List;

public class Booking {
    private User user;
    private Long bookingId;
    private Screening screening;
    private Long totalPrice;
    private List<Seat> seats;
    private BookingStatus status;

    public Booking() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void confirm() {
        this.status = BookingStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }

    public void addSeat(Seat seat) {
        if (!seats.contains(seat)) {
            seats.add(seat);
        }
    }

    public void removeSeat(Seat seat) {
        seats.remove(seat);
    }
}

enum BookingStatus {
    PENDING, CONFIRMED, CANCELLED
}
