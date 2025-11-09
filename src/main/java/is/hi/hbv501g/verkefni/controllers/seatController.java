package is.hi.hbv501g.verkefni.controllers;
import is.hi.hbv501g.verkefni.persistence.entities.MovieHall;
import is.hi.hbv501g.verkefni.persistence.entities.Seat;
import is.hi.hbv501g.verkefni.persistence.repositories.MovieHallRepository;
import is.hi.hbv501g.verkefni.persistence.repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")

public class SeatController {
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private MovieHallRepository movieHallRepository;

    // Bæta við sæti í ákveðinn sal
    @PostMapping("/add")
    public ResponseEntity<?> addSeat(@RequestParam Long hallId,
                                     @RequestParam int rowNumber,
                                     @RequestParam int seatNumber,
                                     @RequestParam Integer price) {

        MovieHall hall = movieHallRepository.findById(hallId).orElse(null);
        if (hall == null) {
            return ResponseEntity.status(404).body("Movie hall not found");
        }

        Seat seat = new Seat(rowNumber, seatNumber, hall, price);
        seatRepository.save(seat);

        return ResponseEntity.ok("Seat added successfully");
    }

    // Fá öll sæti í ákveðnum sal
    @GetMapping("/hall/{hallId}")
    public ResponseEntity<?> getSeatsByHall(@PathVariable Long hallId) {
        MovieHall hall = movieHallRepository.findById(hallId).orElse(null);
        if (hall == null) {
            return ResponseEntity.status(404).body("Movie hall not found");
        }

        List<Seat> seats = seatRepository.findAllByMovieHall(hall);
        return ResponseEntity.ok(seats);
    }

    // Merkja sæti sem bókað (eða óbókað)
    @PatchMapping("/{seatId}/status")
    public ResponseEntity<?> updateSeatStatus(@PathVariable Long seatId,
                                              @RequestParam boolean booked) {
        Seat seat = seatRepository.findById(seatId).orElse(null);
        if (seat == null) {
            return ResponseEntity.status(404).body("Seat not found");
        }

        seat.setBooked(booked);
        seatRepository.save(seat);
        return ResponseEntity.ok("Seat status updated");
    }
}
