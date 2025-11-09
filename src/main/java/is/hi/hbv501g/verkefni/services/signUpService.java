package is.hi.hbv501g.verkefni.services;

public interface signUpService {
    boolean signUp(String email, String password);
    void authentication(String email, String password);
    boolean isemailfree(String email);
    boolean isPasswordLegal(String password);

}
