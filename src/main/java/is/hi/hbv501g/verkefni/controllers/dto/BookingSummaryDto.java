package is.hi.hbv501g.verkefni.controllers.dto;

public record BookingSummaryDto(
        Long bookingId,
        Long seatId,
        Long hallId,
        Long movieId,
        String movieTitle,
        String screeningTime,
        String discountCode,
        Integer discountPercent
) {}


