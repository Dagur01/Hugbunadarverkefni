package is.hi.hbv501g.verkefni.controllers.dto;

public class MovieHallCreate {

    public record MovieHallCreateRequest(
            String name,
            String location,
            Boolean nowShowing
    ) {}
}
