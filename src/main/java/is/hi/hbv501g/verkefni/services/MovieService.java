package is.hi.hbv501g.verkefni.services;

import is.hi.hbv501g.verkefni.persistence.entities.Movie;

import java.util.List;

public interface MovieService {

    Movie create(String movieTitle, String genre, Integer ageRating, Long duration);
    Movie update(Long movieId, String movieTitle, String genre);
    void delete(Long movieId);

    List<Movie> listNowPlaying();
    List<Movie> listMovies();
    Movie getMovieById(Long movieId);
    List<Movie> filterMovies(String movieTitle, String genre, Integer ageRating, Long duration);
}
