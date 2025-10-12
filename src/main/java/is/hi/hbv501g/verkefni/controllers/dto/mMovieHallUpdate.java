package is.hi.hbv501g.verkefni.controllers.dto;

public class mMovieHallUpdate {
    public record MovieHallUpdateRequest(
            String name,
            String location,
            Boolean nowShowing
    ) {}
}
