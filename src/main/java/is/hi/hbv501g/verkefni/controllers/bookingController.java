package is.hi.hbv501g.verkefni.controllers;
import org.springframework.web.bind.annotation.*;

import is.hi.hbv501g.verkefni.controllers.dto.BookingCreateDtos;
import is.hi.hbv501g.verkefni.persistence.entities.*;
import is.hi.hbv501g.verkefni.persistence.repositories.*;
import is.hi.hbv501g.verkefni.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieHallRepository movieHallRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<?> createBooking(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody BookingCreateDtos dto
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid token");
        }

        String email = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return ResponseEntity.status(404).body("User not found");

        var movie = movieRepository.findById(dto.getMovieId()).orElse(null);
        if (movie == null) return ResponseEntity.status(404).body("Movie not found");

        var hall = movieHallRepository.findById(dto.getHallId()).orElse(null);
        if (hall == null) return ResponseEntity.status(404).body("Movie hall not found");

        var seat = seatRepository.findById(dto.getSeatId()).orElse(null);
        if (seat == null) return ResponseEntity.status(404).body("Seat not found");

        boolean seatTaken = bookingRepository.existsBySeat(seat);
        if (seatTaken) return ResponseEntity.status(409).body("Seat already booked");

        Booking booking = new Booking();
        booking.setMovie(movie);
        booking.setMovieHall(hall);
        booking.setSeat(seat);
        booking.setUser(user);

        bookingRepository.save(booking);
        return ResponseEntity.ok("Booking created successfully for user: " + user.getEmail());
    }


}
