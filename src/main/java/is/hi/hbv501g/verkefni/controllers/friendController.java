package is.hi.hbv501g.verkefni.controllers;


import is.hi.hbv501g.verkefni.controllers.dto.ProfileDto;
import is.hi.hbv501g.verkefni.persistence.entities.friendRequest;
import is.hi.hbv501g.verkefni.persistence.entities.movieInvitation;
import is.hi.hbv501g.verkefni.security.jwtService;
import is.hi.hbv501g.verkefni.services.friendService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class friendController {

    private static final Logger log = LoggerFactory.getLogger(friendController.class);

    private final friendService friendService;
    private final jwtService jwtService;

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
            friendRequest fr = friendService.sendRequest(fromEmail, toEmail);
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
            friendRequest fr = friendService.acceptRequest(id, actingEmail);
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
            friendRequest fr = friendService.rejectRequest(id, acting);
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

    @GetMapping(path = "/friends/list", produces = "application/json")
    public ResponseEntity<?> listFriends(@RequestHeader("Authorization") String authHeader) {
        String token = extractToken(authHeader);
        if (token == null || !jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or missing token");
        }
        String email = jwtService.extractEmail(token);
        if (email == null || email.isBlank()) {
            return ResponseEntity.status(401).body("Token does not contain an email");
        }
        try {
            var friends = friendService.listFriendsUsernames(email);
            return ResponseEntity.ok(friends);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getClass().getSimpleName(), "message", e.getMessage()));
        }
    }

    // send a movie invitation to a friend

    @PostMapping(path = "/friends/invite", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> inviteToMovie(@RequestHeader("Authorization") String authHeader,
                                           @RequestBody Map<String, Object> body) {
        String token = extractToken(authHeader);
        if (token == null || !jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or missing token");
        }
        String inviterEmail = jwtService.extractEmail(token);
        if (inviterEmail == null || inviterEmail.isBlank()) {
            return ResponseEntity.status(401).body("Token does not contain an email");
        }

        String inviteeEmail = (String) body.get("email");
        Object movieIdObj = body.get("movieId");
        if (inviteeEmail == null || inviteeEmail.isBlank()) return ResponseEntity.badRequest().body("Missing invitee email");
        if (movieIdObj == null) return ResponseEntity.badRequest().body("Missing movieId");

        Long movieId;
        try {
            movieId = Long.valueOf(String.valueOf(movieIdObj));
        } catch (NumberFormatException nfe) {
            return ResponseEntity.badRequest().body("movieId must be a number");
        }


        try {
            movieInvitation inv = friendService.inviteFriendToMovie(inviterEmail, inviteeEmail, movieId);
            return ResponseEntity.ok(inv);
        } catch (NoSuchElementException nse) {
            return ResponseEntity.status(404).body(Map.of("message", nse.getMessage(), "error", nse.getClass().getSimpleName()));
        } catch (SecurityException se) {
            return ResponseEntity.status(403).body(se.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getClass().getSimpleName(), "message", e.getMessage()));
        }
    }


}
