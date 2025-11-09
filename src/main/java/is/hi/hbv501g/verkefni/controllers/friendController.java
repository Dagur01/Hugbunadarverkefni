package is.hi.hbv501g.verkefni.controllers;


import is.hi.hbv501g.verkefni.controllers.dto.ProfileDto;
import is.hi.hbv501g.verkefni.persistence.entities.FriendRequest;
import is.hi.hbv501g.verkefni.security.JwtService;
import is.hi.hbv501g.verkefni.services.FriendService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private static final Logger log = LoggerFactory.getLogger(FriendController.class);

    private final FriendService friendService;
    private final JwtService jwtService;

    private String extractToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        return authHeader.substring(7);
    }

    @PostMapping(path = "/friends/request", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> sendRequest(@RequestHeader("Authorization") String authHeader,
                                         @RequestBody Map<String, String> body) {
        String token = extractToken(authHeader);
        if (token == null || !jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or missing token");
        }
        String fromEmail = jwtService.extractEmail(token);
        if (fromEmail == null || fromEmail.isBlank()) {
            return ResponseEntity.status(401).body("Token does not contain an email");
        }

        String toEmail = body.get("email");
        if (toEmail == null || toEmail.isBlank()) return ResponseEntity.badRequest().body("Missing target email");

        try {
            // Ensure your friendService.sendRequest accepts emails (fromEmail, toEmail)
            FriendRequest fr = friendService.sendRequest(fromEmail, toEmail);
            return ResponseEntity.ok(fr);
        } catch (NoSuchElementException nse) {
            // service couldn't find sender or target -> return 404 with clear message
            return ResponseEntity.status(404).body(Map.of("message", nse.getMessage(), "error", nse.getClass().getSimpleName()));
        } catch (SecurityException se) {
            return ResponseEntity.status(403).body(se.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getClass().getSimpleName(), "message", e.getMessage()));
        }
    }

    @PostMapping(path = "/friends/request/{id}/accept")
    public ResponseEntity<?> acceptRequest(@RequestHeader("Authorization") String authHeader,
                                           @PathVariable Long id) {
        String token = extractToken(authHeader);
        if (token == null || !jwtService.validateToken(token)) return ResponseEntity.status(401).body("Invalid or missing token");
        String actingEmail = jwtService.extractEmail(token);

        try {
            FriendRequest fr = friendService.acceptRequest(id, actingEmail);
            return ResponseEntity.ok(fr);
        } catch (SecurityException se) {
            return ResponseEntity.status(403).body(se.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getClass().getSimpleName(), "message", e.getMessage()));
        }
    }

    @PostMapping(path = "/friends/request/{id}/reject")
    public ResponseEntity<?> rejectRequest(@RequestHeader("Authorization") String authHeader,
                                           @PathVariable Long id) {
        String token = extractToken(authHeader);
        if (token == null || !jwtService.validateToken(token)) return ResponseEntity.status(401).body("Invalid or missing token");
        String acting = jwtService.extractEmail(token);

        try {
            FriendRequest fr = friendService.rejectRequest(id, acting);
            return ResponseEntity.ok(fr);
        } catch (SecurityException se) {
            return ResponseEntity.status(403).body(se.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getClass().getSimpleName(), "message", e.getMessage()));
        }
    }

    @GetMapping(path = "/users/{username}/profile", produces = "application/json")
    public ResponseEntity<?> viewProfile(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                         @PathVariable String username) {
        String viewer = null;
        if (authHeader != null) {
            String token = extractToken(authHeader);
            if (token != null && jwtService.validateToken(token)) {
                viewer = jwtService.extractEmail(token);
            }
        }
        try {
            ProfileDto profile = friendService.getProfile(username, viewer);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getClass().getSimpleName(), "message", e.getMessage()));
        }
    }


}
