package is.hi.hbv501g.verkefni.controllers;

import is.hi.hbv501g.verkefni.persistence.entities.Booking;
import is.hi.hbv501g.verkefni.services.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @PostMapping
    public Booking create(@RequestBody CreateBookingDto dto) {
        return service.create(dto.username(), dto.screeningId(), dto.seatIds());
    }

    @PostMapping("/{id}/confirm")
    public Booking confirm(@PathVariable Long id) {
        return service.confirm(id);
    }

    @PostMapping("/{id}/cancel")
    public Booking cancel(@PathVariable Long id) {
        return service.cancel(id);
    }

    @GetMapping("/{id}")
    public Booking get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/me/{username}")
    public List<Booking> mine(@PathVariable String username) {
        return service.listForUser(username);
    }

    public static record CreateBookingDto(String username, Long screeningId, java.util.List<Long> seatIds) {
    }
}
