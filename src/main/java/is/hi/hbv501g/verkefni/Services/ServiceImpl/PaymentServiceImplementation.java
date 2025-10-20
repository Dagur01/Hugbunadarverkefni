package is.hi.hbv501g.verkefni.Services.ServiceImpl;

import is.hi.hbv501g.verkefni.Persistence.Entities.Payment;
import is.hi.hbv501g.verkefni.Persistence.Entities.Payment.PaymentMethod;
import is.hi.hbv501g.verkefni.Services.PaymentService;

public class PaymentServiceImplementation implements PaymentService{

    @Override
    public Payment authorize(Long bookingId, PaymentMethod method) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'authorize'");
    }

    @Override
    public Payment refund(Long paymentId, Long amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refund'");
    }
}
