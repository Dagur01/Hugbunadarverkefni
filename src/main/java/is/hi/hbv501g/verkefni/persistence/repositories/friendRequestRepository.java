package is.hi.hbv501g.verkefni.persistence.repositories;

import is.hi.hbv501g.verkefni.persistence.entities.friendRequest;
import is.hi.hbv501g.verkefni.persistence.entities.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface friendRequestRepository extends JpaRepository<friendRequest, Long> {
    List<friendRequest> findByToUserAndStatus(user toUser, String status);
    List<friendRequest> findByFromUserAndStatus(user fromUser, String status);
    List<friendRequest> findByFromUserOrToUserAndStatus(user fromUser, user toUser, String status);
    Optional<friendRequest> findByFromUserAndToUserAndStatus(user fromUser, user toUser, String status);
}
