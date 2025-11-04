package is.hi.hbv501g.verkefni.controllers.dto;

public class MovieHallUpdate {
    public record MovieHallUpdateRequest(
            String name,
            String location,
            Boolean nowShowing
    ) {}
}
