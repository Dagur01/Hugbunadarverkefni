package is.hi.hbv501g.verkefni.Services;

public interface SignUpService {
    boolean register(String usernameInput, String email, String passwordInput, boolean isAdmin);
    void    authentication(String username, String password);
    boolean isEmailFree(String email);
    boolean isPasswordLegal(String password);
}
