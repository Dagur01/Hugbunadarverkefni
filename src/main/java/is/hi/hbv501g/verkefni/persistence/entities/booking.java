package is.hi.hbv501g.verkefni.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingid;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private MovieHall movieHall;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @Column
    private String discountCode;

    @Column
    private Integer discountPercent;

    public Booking() {
    }

    // Fixed setters - they now actually set the values!
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setMovieHall(MovieHall movieHall) {
        this.movieHall = movieHall;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }
}
