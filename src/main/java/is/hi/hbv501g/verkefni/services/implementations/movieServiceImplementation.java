package is.hi.hbv501g.verkefni.services.implementations;


import is.hi.hbv501g.verkefni.persistence.entities.movie;
import is.hi.hbv501g.verkefni.persistence.repositories.movieRepository;
import is.hi.hbv501g.verkefni.services.movieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class movieServiceImplementation implements movieService {

    private final movieRepository movieRepository;

    @Override
    public movie create (String Title, String genre, Integer ageRating, Long duration){
        movie movie = is.hi.hbv501g.verkefni.persistence.entities.movie.builder()
                .title(Title)
                .genre(genre)
                .ageRating(ageRating)
                .duration(duration)
                .nowShowing(false)
                .build();
        return movieRepository.save(movie);

    }

    @Override
    public movie update(Long movieId, String movieTitle, String genre, Integer ageRating, Long duration, Boolean nowShowing){
        movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->  new IllegalArgumentException("Movie not found"));
        movie.setTitle(movieTitle);
        movie.setGenre(genre);
        movie.setAgeRating(ageRating);
        movie.setDuration(duration);
        movie.setNowShowing(nowShowing);
        return movieRepository.save(movie);
    }

    @Override
    public void delete(Long movieId){
        movieRepository.deleteById(movieId);
    }

    @Override
    public List<movie> listNowPlaying(){
        return movieRepository.findByNowShowingTrue();
    }

    @Override
    public List<movie> listMovies() {
        return movieRepository.findAll();
    }

    @Override
    public movie getMovieById(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
    }

    @Override
    public List<movie> filterMovies(String movieTitle, String genre, Integer ageRating, Long duration) {
        return movieRepository.findAll().stream()
                .filter(m -> movieTitle == null || m.getTitle().toLowerCase().contains(movieTitle.toLowerCase()))
                .filter(m -> genre == null || m.getGenre().equalsIgnoreCase(genre))
                .filter(m -> ageRating == null || m.getAgeRating().equals(ageRating))
                .filter(m -> duration == null || m.getDuration().equals(duration))
                .toList();
    }
}
