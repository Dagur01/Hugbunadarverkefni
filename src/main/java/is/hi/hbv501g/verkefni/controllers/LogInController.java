package is.hi.hbv501g.verkefni.controllers;

import is.hi.hbv501g.verkefni.controllers.dto.authDtos;
import is.hi.hbv501g.verkefni.persistence.repositories.UserRepository;
import is.hi.hbv501g.verkefni.security.jwtService;
import is.hi.hbv501g.verkefni.services.LogInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LogInController {

    private final LogInService logInService;
    private final UserRepository userRepository;
    private final jwtService jwtService;

    public LogInController() {
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody authDtos.AuthRequest req) {
        boolean ok = logInService.login(req.email(), req.password());
        if (!ok) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }


        String user = userRepository.findByEmail(req.email())
                .map(u -> {
                    return u.getRole().name();
                })
                .orElse("USER");
        String token = jwtService.generateToken(req.email(), Map.of("role", user));
        return ResponseEntity.ok(new authDtos.AuthResponse(token));

    }


}
