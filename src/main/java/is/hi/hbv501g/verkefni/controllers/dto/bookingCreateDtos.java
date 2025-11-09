package is.hi.hbv501g.verkefni.controllers.dto;

public class BookingCreateDtos {
    private Long movieId;
    private Long hallId;
    private Long seatId;
    private Long screeningId;
    private String discountCode;

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public Long getHallId() { return hallId; }
    public void setHallId(Long hallId) { this.hallId = hallId; }

    public Long getSeatId() { return seatId; }
    public void setSeatId(Long seatId) { this.seatId = seatId; }

    public Long getScreeningId() { return screeningId; }
    public void setScreeningId(Long screeningId) { this.screeningId = screeningId; }

    public String getDiscountCode() { return discountCode; }

    public void setDiscountCode(String discountCode) { this.discountCode = discountCode; }
}
