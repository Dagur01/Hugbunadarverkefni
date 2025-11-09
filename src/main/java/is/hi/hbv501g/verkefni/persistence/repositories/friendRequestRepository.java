package is.hi.hbv501g.verkefni.persistence.repositories;

import is.hi.hbv501g.verkefni.persistence.entities.FriendRequest;
import is.hi.hbv501g.verkefni.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByToUserAndStatus(User toUser, String status);
    List<FriendRequest> findByFromUserAndStatus(User fromUser, String status);
    List<FriendRequest> findByFromUserOrToUserAndStatus(User fromUser, User toUser, String status);
    Optional<FriendRequest> findByFromUserAndToUserAndStatus(User fromUser, User toUser, String status);
}
