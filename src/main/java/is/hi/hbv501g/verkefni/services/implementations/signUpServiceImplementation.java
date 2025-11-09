package is.hi.hbv501g.verkefni.services.implementations;



import is.hi.hbv501g.verkefni.persistence.entities.user;
import is.hi.hbv501g.verkefni.persistence.repositories.userRepository;
import is.hi.hbv501g.verkefni.services.signUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class signUpServiceImplementation implements signUpService {

    private final userRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public boolean signUp(String email, String password){
        if (!isemailfree(email) || !isPasswordLegal(password)) {
            return false;
        }
        user user = is.hi.hbv501g.verkefni.persistence.entities.user.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .role(is.hi.hbv501g.verkefni.persistence.entities.user.Role.USER)
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public void authentication(String email, String password){
        if (!isPasswordLegal(password)) throw new IllegalArgumentException("Weak password");
    }

    @Override
    public boolean isemailfree(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    public boolean isPasswordLegal(String password) {
        return password != null && password.length() >= 8;
    }


}
