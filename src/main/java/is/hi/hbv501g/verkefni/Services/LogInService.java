package is.hi.hbv501g.verkefni.Services;

public interface LogInService {
    boolean login(String usernameInput, String passwordInput);
    void    logOut(Long userId);
    boolean validUser(String username);
    boolean isAdmin(String usernameInput, String passwordInput);
    void    authenticate(String usernameInput, String passwordInput);
}