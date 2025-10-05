package is.hi.hbv501g.verkefni.services.implementations;


import is.hi.hbv501g.verkefni.persistence.entities.User;
import is.hi.hbv501g.verkefni.persistence.repositories.UserRepository;
import is.hi.hbv501g.verkefni.services.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpServiceImplementation implements SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean signUp(String email, String password){
        if (!isEmailFree(email) || !isPasswordLegal(password)) {
            return false;
        }
        User user = User.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .role(User.Role.USER)
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public void authentication(String email, String password){
        if (!isPasswordLegal(password)) throw new IllegalArgumentException("Weak password");
    }

    @Override
    public boolean isEmailFree(String email) {
        return !userRepository.isemailfree(email);
    }

    @Override
    public boolean isPasswordLegal(String password) {
        return password != null && password.length() >= 8;
    }

}
