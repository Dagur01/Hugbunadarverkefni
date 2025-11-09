package is.hi.hbv501g.verkefni.controllers;

import is.hi.hbv501g.verkefni.controllers.dto.bookingCreateDtos;
import is.hi.hbv501g.verkefni.persistence.entities.booking;
import is.hi.hbv501g.verkefni.services.DiscountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/bookings")
public class bookingController {
    @Autowired
    private is.hi.hbv501g.verkefni.persistence.repositories.movieRepository movieRepository;
    @Autowired
    private is.hi.hbv501g.verkefni.persistence.repositories.movieHallRepository movieHallRepository;
    @Autowired
    private is.hi.hbv501g.verkefni.persistence.repositories.seatRepository seatRepository;
    @Autowired
    private is.hi.hbv501g.verkefni.persistence.repositories.bookingRepository bookingRepository;
    @Autowired
    private is.hi.hbv501g.verkefni.persistence.repositories.userRepository userRepository;
    @Autowired
    private is.hi.hbv501g.verkefni.persistence.repositories.screeningRepository screeningRepository;
    @Autowired
    private is.hi.hbv501g.verkefni.security.jwtService jwtService;
    @Autowired
    private DiscountService discountService;
    private static final Logger log = LoggerFactory.getLogger(bookingController.class);


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

        Integer discountPercent = null;
        String normalizedCode = null;

        if (dto.getDiscountCode() != null && !dto.getDiscountCode().isBlank()) {
            try {
                var discount = discountService.validateAndUseCode(dto.getDiscountCode());
                discountPercent = discount.getPercentage();
                normalizedCode = discount.getCode();
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest().body(ex.getMessage());
            }
        }

        booking booking = new booking();
        booking.setMovie(movie);
        booking.setMovieHall(hall);
        booking.setSeat(seat);
        booking.setUser(user);
        booking.setScreening(screening);
        booking.setDiscountCode(normalizedCode);
        booking.setDiscountPercent(discountPercent);
        bookingRepository.save(booking);
        String timeText = screening.getScreeningTime().toString(); // or the formatter shown above

        String discountText = "";
        if (normalizedCode != null && discountPercent != null) {
            discountText = " (code " + normalizedCode + ", " + discountPercent + "%)";
        }

        System.out.println("DTO code: '" + dto.getDiscountCode() + "'");
        System.out.println("Applied code: " + normalizedCode + ", percent: " + discountPercent);
        return ResponseEntity.ok("Booking created successfully for " + user.getEmail() +
                " at time " + timeText + discountText
        );
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> cancelBooking(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long bookingId
    ) {
        // 1️⃣ Athuga token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid token");
        }

        // 2️⃣ Finna user út frá token
        String email = jwtService.extractEmail(token);
        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        // 3️⃣ Finna bókunina
        var booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            return ResponseEntity.status(404).body("Booking not found");
        }

        // 4️⃣ Athuga hvort user megi cancela
        boolean isAdmin = user.getRole() == is.hi.hbv501g.verkefni.persistence.entities.user.Role.ADMIN;
        boolean isOwner = booking.getUser().getUserId() == user.getUserId();

        if (!isAdmin && !isOwner) {
            return ResponseEntity.status(403).body("You can only cancel your own bookings");
        }

        // 5️⃣ Eyða bókun
        bookingRepository.delete(booking);

        return ResponseEntity.ok("Booking cancelled successfully by " + user.getEmail());
    }
}
