package is.hi.hbv501g.verkefni.persistence.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "seats")
@Getter
@Setter

public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    private int rowNumber;
    private int seatNumber;
    private Integer price;

    private boolean isBooked = false;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    @JsonBackReference
    private MovieHall movieHall;

    public Seat() {}

    public Seat(int rowNumber, int seatNumber, MovieHall movieHall, Integer price) {
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.price = price;
        this.movieHall = movieHall;
    }

}
