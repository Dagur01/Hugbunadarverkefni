package is.hi.hbv501g.verkefni.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import is.hi.hbv501g.verkefni.persistence.entities.movie;
import is.hi.hbv501g.verkefni.persistence.entities.user;
import is.hi.hbv501g.verkefni.persistence.repositories.movieRepository;
import is.hi.hbv501g.verkefni.persistence.repositories.userRepository;
import is.hi.hbv501g.verkefni.security.jwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {
    @Autowired
    private userRepository userRepository;

    @Autowired
    private movieRepository movieRepository;

    @Autowired
    private jwtService jwtService;

    // 🔹 Add a favorite movie
    @PostMapping("/{movieId}")
    public ResponseEntity<?> addFavorite(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long movieId
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid token");
        }

        String email = jwtService.extractUsername(token);
        user user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return ResponseEntity.status(404).body("User not found");

        movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) return ResponseEntity.status(404).body("Movie not found");

        // Check if already favorite
        if (user.getFavoriteMovies().contains(movie)) {
            return ResponseEntity.status(409).body("Movie already in favorites");
        }

        user.addFavoriteMovie(movie);
        userRepository.save(user);

        return ResponseEntity.ok("Movie added to favorites");
    }

    // 🔹 Remove a favorite movie
    @DeleteMapping("/{movieId}")
    public ResponseEntity<?> removeFavorite(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long movieId
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid token");
        }

        String email = jwtService.extractUsername(token);
        user user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return ResponseEntity.status(404).body("User not found");

        movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) return ResponseEntity.status(404).body("Movie not found");

        if (!user.getFavoriteMovies().contains(movie)) {
            return ResponseEntity.status(404).body("Movie not in favorites");
        }

        user.removeFavoriteMovie(movie);
        userRepository.save(user);

        return ResponseEntity.ok("Movie removed from favorites");
    }

    // 🔹 Get all favorite movies
    @GetMapping
    public ResponseEntity<?> getFavorites(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid token");
        }

        String email = jwtService.extractUsername(token);
        user user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return ResponseEntity.status(404).body("User not found");

        return ResponseEntity.ok(user.getFavoriteMovies());
    }

}
