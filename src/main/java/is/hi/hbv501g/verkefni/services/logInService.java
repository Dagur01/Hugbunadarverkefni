package is.hi.hbv501g.verkefni.services;

public interface LogInService {
    static boolean login(String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }
    void logout(Long userId);
    boolean validUser(boolean doesUserExist);
    boolean isAdmin(String email);
    void authentication(String email, String password);
}
