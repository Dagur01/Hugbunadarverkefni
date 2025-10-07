package is.hi.hbv501g.verkefni.controllers;
import is.hi.hbv501g.verkefni.controllers.dto.authDtos;
import is.hi.hbv501g.verkefni.persistence.repositories.userRepository;
import is.hi.hbv501g.verkefni.services.logInService;
import is.hi.hbv501g.verkefni.security.jwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class logInController {

    private  final logInService logInService;
    private final userRepository userRepository;
    private final jwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody authDtos.AuthRequest req){
        boolean ok = logInService.login(req.email(), req.password());
        if (!ok) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String user = userRepository.findByEmail(req.email())
                .map(u -> u.getRole().name())
                .orElse("USER");
        String token = jwtService.generateToken(req.email(), Map.of("role", user));
        return ResponseEntity.ok(new authDtos.AuthResponse(token));
    }

    // Example protected endpoint that verifies role
    @GetMapping("/admin-only")
    public ResponseEntity<?> adminOnly(@RequestHeader("Authorization") String authHeader) {
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

        return ResponseEntity.ok("Admin content");
    }



}
