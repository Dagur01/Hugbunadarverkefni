package is.hi.hbv501g.verkefni.services;

import is.hi.hbv501g.verkefni.persistence.entities.Screening;

import java.util.List;

public interface ScreeningService {
    Screening create(Screening s);

    Screening get(Long id);

    List<Screening> listByMovie(Long movieId);

    Screening update(Long id, Screening update);

    void delete(Long id);
}
