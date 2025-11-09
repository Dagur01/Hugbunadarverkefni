package is.hi.hbv501g.verkefni.controllers;


import is.hi.hbv501g.verkefni.controllers.dto.MovieCreate;
import is.hi.hbv501g.verkefni.controllers.dto.MovieUpdate;
import is.hi.hbv501g.verkefni.persistence.entities.Movie;
import is.hi.hbv501g.verkefni.persistence.repositories.MovieRepository;
import is.hi.hbv501g.verkefni.security.JwtService;
import is.hi.hbv501g.verkefni.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final JwtService jwtService;
    private final MovieRepository movieRepository;

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
    public List<Movie> listMovies() {
        return movieService.listMovies();
    }

    @GetMapping(path = "/movies/filter", produces = "application/json")
    public List<Movie> filterMovies(String movieTitle, String genre, Integer ageRating, Long duration) {
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
    public List<Movie> getMoviesByGenre(@PathVariable String genre) {
        return movieService.getMoviesByGenre(genre);
    }

    // admin - create
    @PostMapping(path = "/movies", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@RequestHeader("Authorization") String authHeader,
                                    @RequestBody MovieCreate.MovieCreateRequest req) {
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
            Movie created = movieService.create(req.title(), req.genre(), req.ageRating(), req.duration());
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
                                    @RequestBody MovieUpdate.MovieUpdateRequest req) {
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
            Movie updated = movieService.update(movieId, req.title(), req.genre(), req.ageRating(), req.duration(), req.nowShowing());
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of("error", e.getClass().getSimpleName(),
                            "message", e.getMessage())
            );
        }
    }

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

    @PatchMapping(path = "/movies/{movieId}/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMoviePicture(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long movieId,
            @RequestPart("file") MultipartFile file
    ) {
        //  1. Staðfesta JWT token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid token");
        }

        //  2. Finna kvikmyndina
        var movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            return ResponseEntity.status(404).body("Movie not found");
        }

        //  3. Staðfesta skrána
        if (file.isEmpty()) return ResponseEntity.badRequest().body("Empty file");

        String ct = file.getContentType();
        if (ct == null || !(ct.equalsIgnoreCase(MediaType.IMAGE_JPEG_VALUE)
                || ct.equalsIgnoreCase(MediaType.IMAGE_PNG_VALUE))) {
            return ResponseEntity.status(415).body("Only JPEG or PNG allowed");
        }

        if (file.getSize() > 2 * 1024 * 1024) {
            return ResponseEntity.badRequest().body("File too large (max 2 MB)");
        }

        //  4. Vista myndina í gagnagrunninn
        try {
            movie.setMoviePicture(file.getBytes());
            movie.setMoviePictureContentType(ct);
            movieRepository.save(movie);
            return ResponseEntity.ok("Movie poster updated successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to read file");
        }
    }





}
