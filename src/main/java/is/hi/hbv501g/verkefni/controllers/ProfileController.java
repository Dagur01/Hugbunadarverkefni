package is.hi.hbv501g.verkefni.controllers;

import is.hi.hbv501g.verkefni.controllers.dto.profileDtos;
import is.hi.hbv501g.verkefni.persistence.repositories.bookingRepository;
import is.hi.hbv501g.verkefni.persistence.repositories.userRepository;
import is.hi.hbv501g.verkefni.security.jwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final userRepository userRepository;
    private final jwtService jwtService;
    private final bookingRepository bookingRepository;



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
        String email = jwtService.extractEmail(token);

        return userRepository.findByEmail(email)
                .map(u -> {
                    String base64 = null, dataUrl = null;
                    if (u.getProfilePicture() != null && u.getProfilePicture().length > 0) {
                        base64 = java.util.Base64.getEncoder().encodeToString(u.getProfilePicture());
                        String ct = (u.getProfilePictureContentType() != null) ? u.getProfilePictureContentType() : "image/jpeg";
                        dataUrl = "data:" + ct + ";base64," + base64;
                    }
                    return ResponseEntity.ok(
                            new profileDtos.ProfileResponse(u.getEmail(), u.getUsername(), base64)
                    );
                })
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @GetMapping(path = "/profile/bookings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserBookings(
            @RequestHeader("Authorization") String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid token");
        }
        String email = jwtService.extractEmail(token);
        var user = userRepository.findByEmail(email)
                .orElse(null);
        if (user == null) return ResponseEntity.status(404).body("User not found");
        var bookings = bookingRepository.findAllByUser(user);
        return ResponseEntity.ok(bookings);
    }


    // Update logged in user's profile username
    @PatchMapping(path = "/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody profileDtos.ProfileUpdateRequest req
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid token");
        }
        String email = jwtService.extractEmail(token);

        var user = userRepository.findByEmail(email)
                .orElse(null);
        if (user == null) return ResponseEntity.status(404).body("User not found");

        if (req.username() != null && !req.username().isBlank()) {
            user.setUsername(req.username());
            userRepository.save(user);
        }
        return ResponseEntity.ok("Username updated");
    }

    // Upload or update profile picture
    @PatchMapping(path = "/profile/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProfilePicture(
            @RequestHeader("Authorization") String authHeader,
            @RequestPart("file") MultipartFile file
    ) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid token");
        }
        String email = jwtService.extractEmail(token);


        var user = userRepository.findByEmail(email)
                .orElse(null);
        if (user == null) return ResponseEntity.status(404).body("User not found");


        if (file.isEmpty()) return ResponseEntity.badRequest().body("Empty file");

        String ct = file.getContentType();
        // allow jpeg & png
        if (ct == null || !(ct.equalsIgnoreCase(MediaType.IMAGE_JPEG_VALUE)
                || ct.equalsIgnoreCase(MediaType.IMAGE_PNG_VALUE))) {
            return ResponseEntity.status(415).body("Only JPEG or PNG allowed");
        }

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
