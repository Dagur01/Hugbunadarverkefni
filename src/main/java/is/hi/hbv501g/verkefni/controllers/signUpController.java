package is.hi.hbv501g.verkefni.controllers;



import is.hi.hbv501g.verkefni.security.JwtService;
import is.hi.hbv501g.verkefni.services.SignUpService;
import is.hi.hbv501g.verkefni.controllers.dto.AuthDtos;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SignUpController {

    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody AuthDtos.SignUpRequest req) {
        boolean ok = SignUpService.signUp(req.email(), req.password());
        if (!ok) {
            return ResponseEntity.badRequest().body("Email taken or weak password");
        }

        String token = jwtService.generateToken(req.email(), Map.of("role", "USER"));
        return ResponseEntity.ok(new AuthDtos.AuthResponse(token));
    }



}
