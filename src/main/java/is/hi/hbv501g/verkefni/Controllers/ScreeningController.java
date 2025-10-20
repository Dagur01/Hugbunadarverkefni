package is.hi.hbv501g.verkefni.Controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import is.hi.hbv501g.verkefni.Persistence.Entities.Screening;
import is.hi.hbv501g.verkefni.Services.ScreeningService;


@RestController
@RequestMapping("/api/screenings")
public class ScreeningController {
    private final ScreeningService service;
    public ScreeningController(ScreeningService service) { this.service = service; }

    @PostMapping public Screening create(@RequestBody Screening s) { return service.create(s); }
    @GetMapping("/{id}") public Screening get(@PathVariable Long id) { return service.get(id); }
    @GetMapping("/by-movie/{movieId}") public List<Screening> byMovie(@PathVariable Long movieId) { return service.listByMovie(movieId); }
    @PutMapping("/{id}") public Screening update(@PathVariable Long id, @RequestBody Screening u) { return service.update(id, u); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}
