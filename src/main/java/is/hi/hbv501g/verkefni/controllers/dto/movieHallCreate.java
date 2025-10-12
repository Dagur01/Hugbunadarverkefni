package is.hi.hbv501g.verkefni.controllers.dto;

public class movieHallCreate {

    public record MovieHallCreateRequest(
            String name,
            String location,
            Boolean nowShowing
    ) {}
}
