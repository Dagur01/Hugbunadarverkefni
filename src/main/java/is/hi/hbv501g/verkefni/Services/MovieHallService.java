package is.hi.hbv501g.verkefni.Services;

import java.util.List;
import is.hi.hbv501g.verkefni.Persistence.Entities.MovieHall;

public interface MovieHallService {
    MovieHall create(MovieHall h);
    MovieHall get(Long id);
    List<MovieHall> list();
    MovieHall update(Long id, MovieHall update);
    void delete(Long id);
    void changeCapacity(Long id, int totalSeats);
}

