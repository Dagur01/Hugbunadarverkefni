package is.hi.hbv501g.verkefni.Services;

import java.util.List;
import is.hi.hbv501g.verkefni.Persistence.Entities.Movie;
public interface FavoriteService {
    void add(String username, Long movieId);
    void remove(String username, Long movieId);
    List<Movie> list(String username);
}
