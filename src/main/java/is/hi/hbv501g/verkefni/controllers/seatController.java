package is.hi.hbv501g.verkefni.controllers;
import is.hi.hbv501g.verkefni.persistence.entities.movieHall;
import is.hi.hbv501g.verkefni.persistence.entities.seat;
import is.hi.hbv501g.verkefni.persistence.repositories.movieHallRepository;
import is.hi.hbv501g.verkefni.persistence.repositories.seatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")

public class seatController {
    @Autowired
    private seatRepository seatRepository;

    @Autowired
    private movieHallRepository movieHallRepository;

    // Bæta við sæti í ákveðinn sal
    @PostMapping("/add")
    public ResponseEntity<?> addSeat(@RequestParam Long hallId,
                                     @RequestParam int rowNumber,
                                     @RequestParam int seatNumber) {

        movieHall hall = movieHallRepository.findById(hallId).orElse(null);
        if (hall == null) {
            return ResponseEntity.status(404).body("Movie hall not found");
        }

        seat seat = new seat(rowNumber, seatNumber, hall);
        seatRepository.save(seat);

        return ResponseEntity.ok("Seat added successfully");
    }

    // Fá öll sæti í ákveðnum sal
    @GetMapping("/hall/{hallId}")
    public ResponseEntity<?> getSeatsByHall(@PathVariable Long hallId) {
        movieHall hall = movieHallRepository.findById(hallId).orElse(null);
        if (hall == null) {
            return ResponseEntity.status(404).body("Movie hall not found");
        }

        List<seat> seats = seatRepository.findAllByMovieHall(hall);
        return ResponseEntity.ok(seats);
    }

    // Merkja sæti sem bókað (eða óbókað)
    @PatchMapping("/{seatId}/status")
    public ResponseEntity<?> updateSeatStatus(@PathVariable Long seatId,
                                              @RequestParam boolean booked) {
        seat seat = seatRepository.findById(seatId).orElse(null);
        if (seat == null) {
            return ResponseEntity.status(404).body("Seat not found");
        }

        seat.setBooked(booked);
        seatRepository.save(seat);
        return ResponseEntity.ok("Seat status updated");
    }
}
