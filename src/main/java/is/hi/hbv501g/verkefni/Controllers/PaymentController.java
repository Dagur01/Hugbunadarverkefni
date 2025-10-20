package is.hi.hbv501g.verkefni.Controllers;

import org.springframework.web.bind.annotation.*;
import is.hi.hbv501g.verkefni.Persistence.Entities.Payment;
import is.hi.hbv501g.verkefni.Services.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/authorize/{bookingId}")
    public Payment authorize(@PathVariable Long bookingId,
                             @RequestParam Payment.PaymentMethod method) {
        return service.authorize(bookingId, method);
    }

    @PostMapping("/{paymentId}/refund")
    public Payment refund(@PathVariable Long paymentId,
                          @RequestParam Long amount) {
        return service.refund(paymentId, amount);
    }
}


