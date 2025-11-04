package is.hi.hbv501g.verkefni.persistence.entities;

import ch.qos.logback.core.status.Status;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "payments")
public class Payment {
  @Id
  private Long paymentId;

  @ManyToOne(optional = false) 
  @JoinColumn(name = "booking_id")
  private Booking booking;

  @Column(nullable = false)
  private Long amountInCents; // eða BigDecimal

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status; // PENDING, PAID, FAILED, REFUNDED

  @Column(nullable = false)
  private String provider; // "stripe", "cash", ...

  @Column(nullable = false)
  private Instant createdAt = Instant.now();
}

