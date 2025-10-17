package is.hi.hbv501g.verkefni.persistence.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "seats")

public class seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    private int rowNumber;
    private int seatNumber;

    private boolean isBooked = false;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    private movieHall movieHall;

    public seat() {}

    public seat(int rowNumber, int seatNumber, movieHall movieHall) {
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.movieHall = movieHall;
    }

    // Getters & Setters
    public Long getId() { return seatId; }

    public int getRowNumber() { return rowNumber; }
    public void setRowNumber(int rowNumber) { this.rowNumber = rowNumber; }

    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }

    public boolean isBooked() { return isBooked; }
    public void setBooked(boolean booked) { isBooked = booked; }

    public movieHall getMovieHall() { return movieHall; }
    public void setMovieHall(movieHall movieHall) { this.movieHall = movieHall; }
}
