package is.hi.hbv501g.verkefni.controllers;
import is.hi.hbv501g.verkefni.controllers.dto.authDtos;
import is.hi.hbv501g.verkefni.controllers.dto.profileDtos;
import is.hi.hbv501g.verkefni.persistence.repositories.userRepository;
import is.hi.hbv501g.verkefni.services.logInService;
import is.hi.hbv501g.verkefni.security.jwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class logInController {

    private final logInService logInService;
    private final userRepository userRepository;
    private final jwtService jwtService;

    // User login
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

    // Get logged in user's profile
    @GetMapping(path = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid token");
        }
        String email = jwtService.extractUsername(token);

        return userRepository.findByEmail(email)
                .map(u -> {
                    String base64 = null, dataUrl = null;
                    if (u.getProfilePicture() != null && u.getProfilePicture().length > 0) {
                        base64 = java.util.Base64.getEncoder().encodeToString(u.getProfilePicture());
                        String ct = (u.getProfilePictureContentType() != null) ? u.getProfilePictureContentType() : "image/jpeg";
                        dataUrl = "data:" + ct + ";base64," + base64;
                    }
                    return ResponseEntity.ok(
                            new profileDtos.ProfileResponse(u.getEmail(), u.getUsername(), base64) // or add dataUrl if you extend the DTO
                    );
                })
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

}
