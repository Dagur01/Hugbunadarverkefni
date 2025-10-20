package is.hi.hbv501g.verkefni.Services;

import is.hi.hbv501g.verkefni.Persistence.Entities.Payment;

public interface PaymentService {
    Payment authorize(Long bookingId, Payment.PaymentMethod method);
    Payment refund(Long paymentId, Long amount);
}
