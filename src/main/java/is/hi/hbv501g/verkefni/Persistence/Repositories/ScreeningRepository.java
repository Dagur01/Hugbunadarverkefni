package is.hi.hbv501g.verkefni.Persistence.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import is.hi.hbv501g.verkefni.Persistence.Entities.Screening;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    List<Screening> findByMovieMovieId(Long movieId);
}
