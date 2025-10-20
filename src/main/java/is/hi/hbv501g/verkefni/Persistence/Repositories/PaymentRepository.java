package is.hi.hbv501g.verkefni.Persistence.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import is.hi.hbv501g.verkefni.Persistence.Entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}
