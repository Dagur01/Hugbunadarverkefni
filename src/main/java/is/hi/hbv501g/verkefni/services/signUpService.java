package is.hi.hbv501g.verkefni.services;

public interface SignUpService {
    static boolean signUp(String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'signUp'");
    }
    void authentication(String email, String password);
    boolean isemailfree(String email);
    boolean isPasswordLegal(String password);
}
