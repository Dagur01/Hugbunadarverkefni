package is.hi.hbv501g.verkefni.persistence.repositories;

import is.hi.hbv501g.verkefni.persistence.entities.movieInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface movieInvitationRepository extends JpaRepository<movieInvitation, Long> {
    List<movieInvitation> findByInvitee_Email(String email);
    List<movieInvitation> findByInviter_Email(String email);
}
