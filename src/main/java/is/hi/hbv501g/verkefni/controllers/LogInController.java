package is.hi.hbv501g.verkefni.controllers;
import is.hi.hbv501g.verkefni.controllers.dto.AuthDtos;
import is.hi.hbv501g.verkefni.persistence.repositories.UserRepository;
import is.hi.hbv501g.verkefni.services.LogInService;
import is.hi.hbv501g.verkefni.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LogInController {

    private  final LogInService logInService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDtos.AuthRequest req){
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
        return ResponseEntity.ok(new AuthDtos.AuthResponse(token));
    }



}
