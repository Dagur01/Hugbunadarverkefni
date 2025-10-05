package is.hi.hbv501g.verkefni.controllers.dto;

public class MovieUpdate {
    public record MovieUpdateRequest(
            String title,
            String genre,
            Integer ageRating,
            Long duration
    ) {}
}
