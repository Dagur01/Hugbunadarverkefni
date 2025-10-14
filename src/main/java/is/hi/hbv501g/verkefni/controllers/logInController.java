package is.hi.hbv501g.verkefni.controllers;
import is.hi.hbv501g.verkefni.controllers.dto.authDtos;
import is.hi.hbv501g.verkefni.controllers.dto.profileDtos;
import is.hi.hbv501g.verkefni.persistence.entities.user;
import is.hi.hbv501g.verkefni.persistence.repositories.userRepository;
import is.hi.hbv501g.verkefni.services.logInService;
import is.hi.hbv501g.verkefni.security.jwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping(path = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Missing or invalid Authorization header");
            }
            String token = authHeader.substring(7);
            if (!jwtService.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid token");
            }
            String email = jwtService.extractUsername(token);
            Optional<user> optUser = userRepository.findByEmail(email);
            if (optUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found");
            }
            user u = optUser.get();
            String picBase64 = null;
            if (u.getProfilePicture() != null && u.getProfilePicture().length > 0) {
                picBase64 = Base64.getEncoder().encodeToString(u.getProfilePicture());
            }
            profileDtos.ProfileResponse response =
                    new profileDtos.ProfileResponse(u.getEmail(), u.getUsername(), picBase64);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error");
        }
    }

    @PatchMapping(path = "/profile", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String authHeader,
                                           @RequestBody profileDtos.ProfileUpdateRequest req) {
        try {
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
                        if (req.username() != null) u.setUsername(req.username());
                        if (req.profilePictureBase64() != null) {
                            byte[] decoded = Base64.getDecoder().decode(req.profilePictureBase64());
                            u.setProfilePicture(decoded);
                        }
                        userRepository.save(u);
                        String picBase64 = u.getProfilePicture() != null
                                ? Base64.getEncoder().encodeToString(u.getProfilePicture())
                                : null;
                        return ResponseEntity.ok(new profileDtos.ProfileResponse(u.getEmail(), u.getUsername(), picBase64));
                    })
                    .orElse(ResponseEntity.status(404).body((profileDtos.ProfileResponse) null));
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(400).body("Invalid image Base64");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server error");
        }
    }

}
