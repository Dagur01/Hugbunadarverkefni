package is.hi.hbv501g.verkefni.controllers;
import is.hi.hbv501g.verkefni.security.jwtService;
import org.springframework.web.bind.annotation.*;
import is.hi.hbv501g.verkefni.controllers.dto.bookingCreateDtos;
import is.hi.hbv501g.verkefni.persistence.entities.*;
import is.hi.hbv501g.verkefni.persistence.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/bookings")
public class bookingController {
    @Autowired private movieRepository movieRepository;
    @Autowired private movieHallRepository movieHallRepository;
    @Autowired private seatRepository seatRepository;
    @Autowired private bookingRepository bookingRepository;
    @Autowired private userRepository userRepository;
    @Autowired private screeningRepository screeningRepository;
    @Autowired private jwtService jwtService;

    @PostMapping
    public ResponseEntity<?> createBooking(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody bookingCreateDtos dto
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid token");
        }

        String email = jwtService.extractEmail(token);
        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return ResponseEntity.status(404).body("User not found");

        var movie = movieRepository.findById(dto.getMovieId()).orElse(null);
        if (movie == null) return ResponseEntity.status(404).body("Movie not found");

        var hall = movieHallRepository.findById(dto.getHallId()).orElse(null);
        if (hall == null) return ResponseEntity.status(404).body("Movie hall not found");

        var seat = seatRepository.findById(dto.getSeatId()).orElse(null);
        if (seat == null) return ResponseEntity.status(404).body("Seat not found");

        var screening = screeningRepository.findById(dto.getScreeningId()).orElse(null);
        if (screening == null) return ResponseEntity.status(404).body("Screening not found");

        boolean seatTaken = bookingRepository.existsBySeat(seat);
        if (seatTaken) return ResponseEntity.status(409).body("Seat already booked");

        booking booking = new booking();
        booking.setMovie(movie);
        booking.setMovieHall(hall);
        booking.setSeat(seat);
        booking.setUser(user);
        booking.setScreening(screening);

        bookingRepository.save(booking);
        return ResponseEntity.ok("Booking created successfully for " + user.getEmail() +
                " at time " + screening.getScreeningTime());
    }
}