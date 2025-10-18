package is.hi.hbv501g.verkefni.controllers;


import is.hi.hbv501g.verkefni.controllers.dto.mMovieHallUpdate;
import is.hi.hbv501g.verkefni.controllers.dto.movieHallCreate;
import is.hi.hbv501g.verkefni.persistence.entities.movieHall;
import is.hi.hbv501g.verkefni.persistence.repositories.movieHallRepository;
import is.hi.hbv501g.verkefni.security.jwtService;
import is.hi.hbv501g.verkefni.services.movieHallService;
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
public class MovieHallControllers {

    private final movieHallService movieHallService;
    private final jwtService jwtService;

    @GetMapping(path = "/now-playing", produces = "application/json")
    public ResponseEntity<?> listNowPlaying() {
        try {
            var movieHalls = movieHallService.listNowPlaying();
            return movieHalls.isEmpty()
                    ? ResponseEntity.notFound().build()
                    : ResponseEntity.ok(movieHalls);
        }catch (Exception e){
            return ResponseEntity.status(500).body(
                    Map.of("error", e.getClass().getSimpleName(),
                            "message", e.getMessage())
            );
        }
    }

    @GetMapping(path = "/movieHalls", produces = "application/json")
    public List<movieHall> listMovieHalls() {
        return movieHallService.listMovieHalls();
    }

    @GetMapping(path = "/movieHalls/filter", produces = "application/json")
    public List<movieHall> filterMovieHalls(String name, String location) {
        return movieHallService.filterMovieHalls(name, location);
    }

    // admin - create
    @PostMapping(path = "/movieHalls", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@RequestHeader("Authorization") String authHeader,
                                    @RequestBody movieHallCreate.MovieHallCreateRequest req) {
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
            movieHall created = movieHallService.create(req.name(), req.location());
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of("error", e.getClass().getSimpleName(),
                            "message", e.getMessage())
            );
        }
    }


    // admin - update
    @PatchMapping(path = "/movieHalls/{movieHallId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String authHeader,
                                    @PathVariable Long movieHallId,
                                    @RequestBody mMovieHallUpdate.MovieHallUpdateRequest req) {
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
            movieHall updated = movieHallService.update(movieHallId, req.name(), req.location(), req.nowShowing());
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of("error", e.getClass().getSimpleName(),
                            "message", e.getMessage())
            );
        }
    }

    // admin - delete
    @DeleteMapping(path = "/movieHalls/{movieHallId}")
    public ResponseEntity<Void> delete(@RequestHeader("Authorization") String authHeader,
                                       @PathVariable Long movieHallId) {
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

        movieHallService.delete(movieHallId);
        return ResponseEntity.noContent().build();
    }


}
