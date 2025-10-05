package is.hi.hbv501g.verkefni.controllers.dto;

public class MovieCreate {
    public record MovieCreateRequest(
            String title,
            String genre,
            Integer ageRating,
            Long duration
    ) {}
}
