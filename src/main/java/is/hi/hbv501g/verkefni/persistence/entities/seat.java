package is.hi.hbv501g.verkefni.persistence.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "seats")
@Getter
@Setter

public class seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    private int rowNumber;
    private int seatNumber;
    private int price;

    private boolean isBooked = false;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    @JsonBackReference
    private movieHall movieHall;

    public seat() {}

    public seat(int rowNumber, int seatNumber, movieHall movieHall, int price) {
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.price = price;
        this.movieHall = movieHall;
    }

}
