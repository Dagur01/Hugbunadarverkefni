package is.hi.hbv501g.verkefni.Controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import is.hi.hbv501g.verkefni.Persistence.Entities.MovieHall;
import is.hi.hbv501g.verkefni.Services.MovieHallService;

@RestController
@RequestMapping("/api/halls")
public class MovieHallController {
    private final MovieHallService service;
    public MovieHallController(MovieHallService service) { this.service = service; }

    @PostMapping public MovieHall create(@RequestBody MovieHall h) { return service.create(h); }
    @GetMapping("/{id}") public MovieHall get(@PathVariable Long id) { return service.get(id); }
    @GetMapping public List<MovieHall> list() { return service.list(); }
    @PutMapping("/{id}") public MovieHall update(@PathVariable Long id, @RequestBody MovieHall u) { return service.update(id, u); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}
