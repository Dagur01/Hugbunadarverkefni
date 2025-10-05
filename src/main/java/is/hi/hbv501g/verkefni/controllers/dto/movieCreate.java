package is.hi.hbv501g.verkefni.controllers.dto;

public class movieCreate {
    public record MovieCreateRequest(
            String title,
            String genre,
            Integer ageRating,
            Long duration
    ) {}
}
