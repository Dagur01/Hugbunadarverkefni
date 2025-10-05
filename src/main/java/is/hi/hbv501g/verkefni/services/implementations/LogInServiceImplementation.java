package is.hi.hbv501g.verkefni.services.implementations;

import is.hi.hbv501g.verkefni.persistence.entities.User;
import is.hi.hbv501g.verkefni.persistence.repositories.UserRepository;
import is.hi.hbv501g.verkefni.services.LogInService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LogInServiceImplementation implements LogInService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public boolean login(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> passwordEncoder.matches(password, user.getPasswordHash()))
                .orElse(false);
    }

    @Override
    public void logout(Long userId) {

    }

    @Override
    public boolean validUser(boolean doesUserExist) {
        return doesUserExist;
    }

    @Override
    public boolean isAdmin(String email) {
        return userRepository.findByEmail(email)
                .map(user -> user.getRole() == User.Role.ADMIN)
                .orElse(false);
    }

    @Override
    public void authentication(String email, String password) {
        userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPasswordHash()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    }
}
