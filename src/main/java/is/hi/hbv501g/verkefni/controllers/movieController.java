package is.hi.hbv501g.verkefni.controllers;


import is.hi.hbv501g.verkefni.controllers.dto.movieCreate;
import is.hi.hbv501g.verkefni.controllers.dto.mMovieUpdate;
import is.hi.hbv501g.verkefni.persistence.entities.movie;
import is.hi.hbv501g.verkefni.services.movieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class movieController {

    private final movieService movieService;

    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity<?> listNowPlaying() {
        try {
            var movies = movieService.listNowPlaying();
            return movies.isEmpty()
                    ? ResponseEntity.notFound().build()
                    : ResponseEntity.ok(movies);
        }catch (Exception e){
            return ResponseEntity.status(500).body(
                    Map.of("error", e.getClass().getSimpleName(),
                            "message", e.getMessage())
            );
        }
    }

    @GetMapping(path = "/movies", produces = "application/json")
    public List<movie> listMovies() {
        return movieService.listMovies();
    }

    @GetMapping(path = "/movies/filter", produces = "application/json")
    public List<movie> filterMovies(String movieTitle, String genre, Integer ageRating, Long duration) {
        return movieService.filterMovies(movieTitle, genre, ageRating, duration);
    }

    //admin
    @PostMapping(path = "/movies", consumes = "application/json", produces = "application/json")
    public movie create(@RequestBody movieCreate.MovieCreateRequest req) {
        return movieService.create(req.title(), req.genre(), req.ageRating(), req.duration());
    }

    @PutMapping(path = "/movies/{movieId}", consumes = "application/json", produces = "application/json")
    public movie update(@PathVariable Long movieId, @RequestBody mMovieUpdate.MovieUpdateRequest req){
        return movieService.update(movieId, req.title(), req.genre());
    }

    @DeleteMapping(path = "/movies/{movieId}")
    public ResponseEntity<Void> delete(@PathVariable Long movieId) {
        movieService.delete(movieId);
        return ResponseEntity.noContent().build();
    }


}
