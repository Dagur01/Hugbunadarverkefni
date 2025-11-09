package is.hi.hbv501g.verkefni.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column

    private String discountCode;

    @Column

    private Integer discountPercent;

    public booking() {

    }

    // Fixed setters - they now actually set the values!

    public void setMovie(movie movie) {

        this.movie = movie;

    }

    public void setMovieHall(movieHall movieHall) {

        this.movieHall = movieHall;

    }

    public void setSeat(seat seat) {

        this.seat = seat;

    }

    public void setUser(user user) {

        this.user = user;

    }

    public void setScreening(screening screening) {

        this.screening = screening;

    }

    public void setDiscountCode(String discountCode) {

        this.discountCode = discountCode;

    }

    public void setDiscountPercent(Integer discountPercent) {

        this.discountPercent = discountPercent;

    }

}
