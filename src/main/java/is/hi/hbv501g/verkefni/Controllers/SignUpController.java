package is.hi.hbv501g.verkefni.controllers;



import is.hi.hbv501g.verkefni.controllers.dto.authDtos;
import is.hi.hbv501g.verkefni.security.jwtService;
import is.hi.hbv501g.verkefni.services.signUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;
    private final jwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody authDtos.SignUpRequest req) {
        boolean ok = SignUpService.register(req.username(), req.email(), req.password(), req.isAdmin());
        if (!ok) {
            return ResponseEntity.badRequest().body("Email taken or weak password");
        }

        String token = jwtService.generateToken(req.email(), Map.of("role", "USER"));
        return ResponseEntity.ok(new authDtos.AuthResponse(token));
    }



}
