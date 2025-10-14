package is.hi.hbv501g.verkefni.controllers.dto;

public class profileDtos {
    private profileDtos() {}

    // profilePictureBase64 holds the image as Base64 string in JSON responses
    public record ProfileResponse(String email, String username, String profilePictureBase64) {}

    // profilePictureBase64 accepts a Base64 string for updates (or null to keep)
    public record ProfileUpdateRequest(String username, String profilePictureBase64) {}
    
}
