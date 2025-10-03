package is.hi.hbv501g.verkefni.Services;

import is.hi.hbv501g.verkefni.Persistence.Entities.User;
import is.hi.hbv501g.verkefni.Persistence.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LogInService {

    private UserRepository userRepository;

    @Autowired
    public LogInService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
