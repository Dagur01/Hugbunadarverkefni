package is.hi.hbv501g.verkefni.services.implementations;


import is.hi.hbv501g.verkefni.persistence.entities.Movie;
import is.hi.hbv501g.verkefni.persistence.repositories.MovieRepository;
import is.hi.hbv501g.verkefni.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImplementation implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Movie create (String Title, String genre, Integer ageRating, Long duration){
        Movie movie = Movie.builder()
                .title(Title)
                .genre(genre)
                .ageRating(ageRating)
                .duration(duration)
                .nowShowing(false)
                .build();
        return movieRepository.save(movie);

    }

    @Override
    public Movie update(Long movieId, String movieTitle, String genre){
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->  new IllegalArgumentException("Movie not found"));
        movie.setTitle(movieTitle);
        movie.setGenre(genre);
        return movieRepository.save(movie);
    }

    @Override
    public void delete(Long movieId){
        movieRepository.deleteById(movieId);
    }

    @Override
    public List<Movie> listNowPlaying(){
        return movieRepository.findByNowShowingTrue();
    }

    @Override
    public List<Movie> listMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
    }

    @Override
    public List<Movie> filterMovies(String movieTitle, String genre, Integer ageRating, Long duration) {
        return movieRepository.findAll().stream()
                .filter(m -> movieTitle == null || m.getTitle().toLowerCase().contains(movieTitle.toLowerCase()))
                .filter(m -> genre == null || m.getGenre().equalsIgnoreCase(genre))
                .filter(m -> ageRating == null || m.getAgeRating().equals(ageRating))
                .filter(m -> duration == null || m.getDuration().equals(duration))
                .toList();
    }
}
