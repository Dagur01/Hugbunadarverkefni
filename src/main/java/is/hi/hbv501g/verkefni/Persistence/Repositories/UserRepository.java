package is.hi.hbv501g.verkefni.Persistence.Repositories;

import is.hi.hbv501g.verkefni.Persistence.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    void delete(User user);
    List<User> findAll();
    void addUser(User user);
}
