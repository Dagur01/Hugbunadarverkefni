package is.hi.hbv501g.verkefni.Controllers;

import org.springframework.web.bind.annotation.*;
import is.hi.hbv501g.verkefni.Services.LogInService;

@RestController
@RequestMapping("/api/auth")
public class LogInController {
    private final LogInService service;
    public LogInController(LogInService service) { this.service = service; }

    @PostMapping("/login")
    public boolean login(@RequestBody LoginDto dto) {
        return service.authenticate(dto.username(), dto.password());
    }

    public record LoginDto(String username, String password) {}
}
