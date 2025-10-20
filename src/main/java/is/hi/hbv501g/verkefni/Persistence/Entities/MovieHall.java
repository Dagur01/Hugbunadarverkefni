package is.hi.hbv501g.verkefni.Persistence.Entities;

public class MovieHall {
    private Long movieHallId;
    private String name;
    private Integer totalSeats;

    public MovieHall() {}

    public Long getMovieHallId() {
        return movieHallId;
    }

    public void setMovieHallId(Long movieHallId) {
        this.movieHallId = movieHallId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }
}

