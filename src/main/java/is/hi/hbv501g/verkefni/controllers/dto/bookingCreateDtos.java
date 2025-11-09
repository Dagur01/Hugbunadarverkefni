package is.hi.hbv501g.verkefni.controllers.dto;

public class bookingCreateDtos {
    private Long movieId;
    private Long hallId;
    private Long seatId;
    private String discountCode;
    private Long screeningId;

    // Getters & setters
    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Long getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(Long screeningId) {
        this.screeningId = screeningId;
    }

    public String getDiscountCode() {
        return discountCode;
    }
}
