package is.hi.hbv501g.verkefni.controllers.dto;

public class movieDtos {
    private movieDtos() {}

    // profilePictureBase64 holds the image as Base64 string in JSON responses
    public record MovieResponse(String genre, String moviePictureBase64) {}

    // profilePictureBase64 accepts a Base64 string for updates (or null to keep)
    public record MovieUpdateRequest(String genre, String moviePictureBase64) {}
}
