package is.hi.hbv501g.verkefni.services;

import is.hi.hbv501g.verkefni.persistence.entities.Payment;

public interface PaymentService {
    Payment authorize(Long bookingId, Payment.PaymentMethod method);

    Payment refund(Long paymentId, Long amount);
}
