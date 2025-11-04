package is.hi.hbv501g.verkefni.services;

import java.util.List;

import is.hi.hbv501g.verkefni.persistence.entities.Movie;

public interface MovieService {

    Movie create(String movieTitle, String genre, Integer ageRating, Long duration);
    Movie update(Long movieId, String movieTitle, String genre, Integer ageRating, Long duration, Boolean nowShowing);
    void delete(Long movieId);

    static List<Movie> listNowPlaying() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listNowPlaying'");
    }
    static List<Movie> listMovies() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listMovies'");
    }
    Movie getMovieById(Long movieId);
    static List<Movie> filterMovies(String movieTitle, String genre, Integer ageRating, Long duration) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filterMovies'");
    }

    List<Movie> getMoviesByGenre(String genre);
}
