package is.hi.hbv501g.verkefni.Controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import is.hi.hbv501g.verkefni.Persistence.Entities.Movie;
import is.hi.hbv501g.verkefni.Services.FavoriteService;

@RestController
@RequestMapping("/api/users/{username}/favorites")
public class FavoritesController {
    private final FavoriteService service;
    public FavoritesController(FavoriteService service) { this.service = service; }

    @PostMapping("/{movieId}") public void add(@PathVariable String username, @PathVariable Long movieId) { service.add(username, movieId); }
    @DeleteMapping("/{movieId}") public void remove(@PathVariable String username, @PathVariable Long movieId) { service.remove(username, movieId); }
    @GetMapping public List<Movie> list(@PathVariable String username) { return service.list(username); }
}
