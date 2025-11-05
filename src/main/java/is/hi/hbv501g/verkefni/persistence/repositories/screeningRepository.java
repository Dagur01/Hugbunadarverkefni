package is.hi.hbv501g.verkefni.persistence.repositories;


import is.hi.hbv501g.verkefni.persistence.entities.screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface screeningRepository extends JpaRepository<screening, Long> {
}
