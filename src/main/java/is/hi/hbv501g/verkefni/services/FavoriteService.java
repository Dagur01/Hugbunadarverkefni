package is.hi.hbv501g.verkefni.services;

import is.hi.hbv501g.verkefni.persistence.entities.Movie;

import java.util.List;

public interface FavoriteService {
    void add(String username, Long movieId);

    void remove(String username, Long movieId);

    List<Movie> list(String username);
}
