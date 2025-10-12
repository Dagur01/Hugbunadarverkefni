package is.hi.hbv501g.verkefni.controllers;


import is.hi.hbv501g.verkefni.controllers.dto.movieCreate;
import is.hi.hbv501g.verkefni.controllers.dto.mMovieUpdate;
import is.hi.hbv501g.verkefni.persistence.entities.movie;
import is.hi.hbv501g.verkefni.security.jwtService;
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
    private final jwtService jwtService;

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

    @GetMapping(path = "/movies/{movieId}", produces = "application/json")
    public ResponseEntity<?> getMovieById(@PathVariable Long movieId) {
        try {
            var movie = movieService.getMovieById(movieId);
            return movie != null
                    ? ResponseEntity.ok(movie)
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of("error", e.getClass().getSimpleName(),
                            "message", e.getMessage())
            );
        }
    }

    @GetMapping(path = "/movies/genre/{genre}", produces = "application/json")
    public List<movie> getMoviesByGenre(@PathVariable String genre) {
        return movieService.getMoviesByGenre(genre);
    }

    // admin - create
    @PostMapping(path = "/movies", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@RequestHeader("Authorization") String authHeader,
                                    @RequestBody movieCreate.MovieCreateRequest req) {
        if (authHeader == null || authHeader.isBlank()) {
            return ResponseEntity.status(401).body("Missing Authorization header");
        }
        if (!jwtService.validateToken(authHeader)) {
            return ResponseEntity.status(401).body("Invalid token");
        }
        String role = jwtService.extractRole(authHeader);
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body("Forbidden: insufficient role");
        }

        try {
            movie created = movieService.create(req.title(), req.genre(), req.ageRating(), req.duration());
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of("error", e.getClass().getSimpleName(),
                            "message", e.getMessage())
            );
        }
    }

    // admin - update
    @PatchMapping(path = "/movies/{movieId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String authHeader,
                                    @PathVariable Long movieId,
                                    @RequestBody mMovieUpdate.MovieUpdateRequest req) {
        if (authHeader == null || authHeader.isBlank()) {
            return ResponseEntity.status(401).body("Missing Authorization header");
        }
        if (!jwtService.validateToken(authHeader)) {
            return ResponseEntity.status(401).body("Invalid token");
        }
        String role = jwtService.extractRole(authHeader);
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body("Forbidden: insufficient role");
        }

        try {
            movie updated = movieService.update(movieId, req.title(), req.genre(), req.ageRating(), req.duration(), req.nowShowing());
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of("error", e.getClass().getSimpleName(),
                            "message", e.getMessage())
            );
        }
    }

    // myndir verða að vera í database, ekki cloudinary

    // admin - delete
    @DeleteMapping(path = "/movies/{movieId}")
    public ResponseEntity<Void> delete(@RequestHeader("Authorization") String authHeader,
                                       @PathVariable Long movieId) {
        if (authHeader == null || authHeader.isBlank()) {
            return ResponseEntity.status(401).build();
        }
        if (!jwtService.validateToken(authHeader)) {
            return ResponseEntity.status(401).build();
        }
        String role = jwtService.extractRole(authHeader);
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).build();
        }

        movieService.delete(movieId);
        return ResponseEntity.noContent().build();
    }


}
