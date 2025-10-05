package is.hi.hbv501g.verkefni.services;

public interface SignUpService {
    boolean signUp(String email, String password);
    void authentication(String email, String password);
    boolean isEmailFree(String email);
    boolean isPasswordLegal(String password);
}
