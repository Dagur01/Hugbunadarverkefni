package is.hi.hbv501g.verkefni.persistence.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
public class booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingid;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private movie movie;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private movieHall movieHall;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private user user;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    private screening screening;

    public booking() {}

}