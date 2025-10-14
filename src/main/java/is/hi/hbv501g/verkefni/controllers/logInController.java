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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PatchMapping(path = "/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody profileDtos.ProfileUpdateRequest req
    ) {
        // --- auth ---
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid token");
        }
        String email = jwtService.extractUsername(token);
        // --- find user ---
        var user = userRepository.findByEmail(email)
                .orElse(null);
        if (user == null) return ResponseEntity.status(404).body("User not found");
        // --- update fields ---
        if (req.username() != null && !req.username().isBlank()) {
            user.setUsername(req.username());
            userRepository.save(user);
        }
        return ResponseEntity.ok("Username updated");
    }

    @PatchMapping(path = "/profile/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProfilePicture(
            @RequestHeader("Authorization") String authHeader,
            @RequestPart("file") MultipartFile file
    ) {
        // --- auth ---
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid token");
        }
        String email = jwtService.extractUsername(token);

        // --- find user ---
        var user = userRepository.findByEmail(email)
                .orElse(null);
        if (user == null) return ResponseEntity.status(404).body("User not found");

        // --- validate file ---
        if (file.isEmpty()) return ResponseEntity.badRequest().body("Empty file");

        String ct = file.getContentType(); // e.g. image/jpeg
        // allow jpeg & png
        if (ct == null || !(ct.equalsIgnoreCase(MediaType.IMAGE_JPEG_VALUE)
                || ct.equalsIgnoreCase(MediaType.IMAGE_PNG_VALUE))) {
            return ResponseEntity.status(415).body("Only JPEG or PNG allowed");
        }
        // optional size limit (e.g. 2 MB)
        if (file.getSize() > 2 * 1024 * 1024) {
            return ResponseEntity.badRequest().body("File too large (max 2 MB)");
        }

        try {
            user.setProfilePicture(file.getBytes());
            user.setProfilePictureContentType(ct);
            userRepository.save(user);
            return ResponseEntity.ok("Profile picture updated");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to read file");
        }
    }

}
