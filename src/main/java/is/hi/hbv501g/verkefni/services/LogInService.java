package is.hi.hbv501g.verkefni.services;

public interface LogInService {
    boolean login(String email, String password);

    void logout(Long userId);

    boolean validUser(boolean doesUserExist);

    boolean isAdmin(String email);

    void authentication(String email, String password);
}
