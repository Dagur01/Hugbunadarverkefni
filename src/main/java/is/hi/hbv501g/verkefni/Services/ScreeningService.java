package is.hi.hbv501g.verkefni.Services;

import java.util.List;
import is.hi.hbv501g.verkefni.Persistence.Entities.Screening;

public interface ScreeningService {
    Screening create(Screening s);
    Screening get(Long id);
    List<Screening> listByMovie(Long movieId);
    Screening update(Long id, Screening update);
    void delete(Long id);
}


