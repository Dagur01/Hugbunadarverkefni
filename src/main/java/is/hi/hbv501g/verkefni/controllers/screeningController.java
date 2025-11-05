package is.hi.hbv501g.verkefni.controllers;


import is.hi.hbv501g.verkefni.persistence.entities.screening;
import is.hi.hbv501g.verkefni.persistence.repositories.screeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/screenings")
public class screeningController {

    @Autowired
    private screeningRepository screeningRepository;

    // ➕ Bæta við nýjum sýningartíma
    @PostMapping
    public ResponseEntity<?> addScreening(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime screeningTime
    ) {
        screening screening = new screening(screeningTime);
        screeningRepository.save(screening);
        return ResponseEntity.ok("Screening time added successfully");
    }

    // 📜 Sækja alla sýningartíma
    @GetMapping
    public List<screening> getAllScreenings() {
        return screeningRepository.findAll();
    }
}