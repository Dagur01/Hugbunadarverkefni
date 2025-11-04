package is.hi.hbv501g.verkefni.persistence.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
  name = "seats",
  uniqueConstraints = @UniqueConstraint(columnNames = {"hall_id", "row_number", "seat_number"})
)
@Getter
@Setter

public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    private int rowNumber;
    private int seatNumber;

    private boolean isBooked = false;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    @JsonBackReference
    private MovieHall movieHall;

    public Seat() {}

    public Seat(int rowNumber, int seatNumber, MovieHall movieHall) {
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.movieHall = movieHall;
    }

}
