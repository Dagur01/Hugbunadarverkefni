package is.hi.hbv501g.verkefni.persistence.entities;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
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

    // 👇 Bættu þessu við:
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private user user;

    public booking() {}

    // getters og setters

    public Long getId() {
        return bookingid;
    }

    public void setId(Long id) {
        this.bookingid = id;
    }

    public movie getMovie() {
        return movie;
    }

    public void setMovie(movie movie) {
        this.movie = movie;
    }

    public movieHall getMovieHall() {
        return movieHall;
    }

    public void setMovieHall(movieHall movieHall) {
        this.movieHall = movieHall;
    }

    public seat getSeat() {
        return seat;
    }

    public void setSeat(seat seat) {
        this.seat = seat;
    }

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }
}
