package is.hi.hbv501g.verkefni.Services;

import java.util.List;
import is.hi.hbv501g.verkefni.Persistence.Entities.Movie;

public interface MovieService {
    Movie create(Movie m);
    Movie get(Long id);
    List<Movie> list();
    Movie update(Long id, Movie update);
    void delete(Long id);
}
