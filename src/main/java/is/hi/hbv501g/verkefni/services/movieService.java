package is.hi.hbv501g.verkefni.services;

import is.hi.hbv501g.verkefni.persistence.entities.movie;

import java.util.List;

public interface movieService {

    movie create(String movieTitle, String genre, Integer ageRating, Long duration);
    movie update(Long movieId, String movieTitle, String genre, Integer ageRating, Long duration, Boolean nowShowing);
    void delete(Long movieId);

    List<movie> listNowPlaying();
    List<movie> listMovies();
    movie getMovieById(Long movieId);
    List<movie> filterMovies(String movieTitle, String genre, Integer ageRating, Long duration);

    List<movie> getMoviesByGenre(String genre);
}
