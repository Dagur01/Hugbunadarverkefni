package is.hi.hbv501g.verkefni.Controllers;

import org.springframework.web.bind.annotation.*;
import is.hi.hbv501g.verkefni.Persistence.Entities.User;
import is.hi.hbv501g.verkefni.Services.ProfileService;

@RestController
@RequestMapping("/api/users")
public class ProfileController {
    private final ProfileService service;
    public ProfileController(ProfileService service) { this.service = service; }

    @GetMapping("/{username}") public User get(@PathVariable String username) { return service.get(username); }

    @PutMapping("/{username}/email")
    public User updateEmail(@PathVariable String username, @RequestBody UpdateEmailDto dto) {
        return service.updateEmail(username, dto.newEmail());
    }

    @PutMapping("/{username}/password")
    public void updatePassword(@PathVariable String username, @RequestBody UpdatePasswordDto dto) {
        service.updatePassword(username, dto.newPassword());
    }

    public record UpdateEmailDto(String newEmail) {}
    public record UpdatePasswordDto(String newPassword) {}
}

