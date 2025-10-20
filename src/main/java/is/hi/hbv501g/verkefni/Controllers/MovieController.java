package is.hi.hbv501g.verkefni.Controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import is.hi.hbv501g.verkefni.Persistence.Entities.Movie;
import is.hi.hbv501g.verkefni.Services.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService service;
    public MovieController(MovieService service) { this.service = service; }

    @PostMapping public Movie create(@RequestBody Movie m) { return service.create(m); }
    @GetMapping("/{id}") public Movie get(@PathVariable Long id) { return service.get(id); }
    @GetMapping public List<Movie> list() { return service.list(); }
    @PutMapping("/{id}") public Movie update(@PathVariable Long id, @RequestBody Movie u) { return service.update(id, u); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}

