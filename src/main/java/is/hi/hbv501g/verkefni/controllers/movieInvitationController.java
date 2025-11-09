package is.hi.hbv501g.verkefni.controllers;


import is.hi.hbv501g.verkefni.persistence.entities.movieInvitation;
import is.hi.hbv501g.verkefni.persistence.repositories.movieInvitationRepository;
import is.hi.hbv501g.verkefni.security.jwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies/invitations")
public class movieInvitationController {

    private final movieInvitationRepository movieInvitationRepository;
    private final jwtService jwtService;

    private String extractToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        return authHeader.substring(7);
    }

    @GetMapping(path = "/sent", produces = "application/json")
    public ResponseEntity<?> listSent(@RequestHeader("Authorization") String authHeader) {
        String token = extractToken(authHeader);
        if (token == null || !jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or missing token");
        }
        String email = jwtService.extractEmail(token);
        if (email == null || email.isBlank()) return ResponseEntity.status(401).body("Token does not contain an email");

        List<movieInvitation> sent = movieInvitationRepository.findByInviter_Email(email);
        return ResponseEntity.ok(sent);
    }

    @GetMapping(path = "/received", produces = "application/json")
    public ResponseEntity<?> listReceived(@RequestHeader("Authorization") String authHeader) {
        String token = extractToken(authHeader);
        if (token == null || !jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or missing token");
        }
        String email = jwtService.extractEmail(token);
        if (email == null || email.isBlank()) return ResponseEntity.status(401).body("Token does not contain an email");

        List<movieInvitation> received = movieInvitationRepository.findByInvitee_Email(email);
        return ResponseEntity.ok(received);
    }

    @PostMapping(path = "/{id}/accept", produces = "application/json")
    public ResponseEntity<?> acceptInvitation(@RequestHeader("Authorization") String authHeader,
                                              @PathVariable Long id) {
        String token = extractToken(authHeader);
        if (token == null || !jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or missing token");
        }
        String actingEmail = jwtService.extractEmail(token);
        if (actingEmail == null || actingEmail.isBlank()) return ResponseEntity.status(401).body("Token does not contain an email");

        try {
            movieInvitation inv = movieInvitationRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Invitation not found"));

            if (!inv.getInvitee().getEmail().equalsIgnoreCase(actingEmail)) {
                return ResponseEntity.status(403).body("Only the invitee can accept the invitation");
            }
            if (!"SENT".equals(inv.getStatus())) {
                return ResponseEntity.badRequest().body("Invitation is not in a state that can be accepted");
            }

            inv.setStatus("ACCEPTED");
            movieInvitation saved = movieInvitationRepository.save(inv);
            return ResponseEntity.ok(saved);
        } catch (NoSuchElementException nse) {
            return ResponseEntity.status(404).body(nse.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping(path = "/{id}/reject", produces = "application/json")
    public ResponseEntity<?> rejectInvitation(@RequestHeader("Authorization") String authHeader,
                                              @PathVariable Long id) {
        String token = extractToken(authHeader);
        if (token == null || !jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or missing token");
        }
        String actingEmail = jwtService.extractEmail(token);
        if (actingEmail == null || actingEmail.isBlank()) return ResponseEntity.status(401).body("Token does not contain an email");

        try {
            movieInvitation inv = movieInvitationRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Invitation not found"));

            if (!inv.getInvitee().getEmail().equalsIgnoreCase(actingEmail)) {
                return ResponseEntity.status(403).body("Only the invitee can reject the invitation");
            }
            if (!"SENT".equals(inv.getStatus())) {
                return ResponseEntity.badRequest().body("Invitation is not in a state that can be rejected");
            }

            inv.setStatus("DECLINED");
            movieInvitation saved = movieInvitationRepository.save(inv);
            return ResponseEntity.ok(saved);
        } catch (NoSuchElementException nse) {
            return ResponseEntity.status(404).body(nse.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}
