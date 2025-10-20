package is.hi.hbv501g.verkefni.controllers.dto;

public class authDtos {
    public record SignUpRequest(String username, String email, String password, boolean isAdmin) {}
    public record AuthRequest(String email, String password) {}
    public record AuthResponse(String token) {}

}
