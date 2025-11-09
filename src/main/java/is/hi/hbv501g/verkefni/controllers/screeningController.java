package is.hi.hbv501g.verkefni.controllers;


import is.hi.hbv501g.verkefni.persistence.entities.Screening;
import is.hi.hbv501g.verkefni.persistence.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    // Bæta við nýjum sýningartíma
    @PostMapping
    public ResponseEntity<?> addScreening(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime screeningTime
    ) {
        Screening screening = new Screening(screeningTime);
        screeningRepository.save(screening);
        return ResponseEntity.ok("Screening time added successfully");
    }

    // Sækja alla sýningartíma
    @GetMapping
    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }
}