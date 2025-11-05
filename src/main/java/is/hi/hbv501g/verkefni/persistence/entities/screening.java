package is.hi.hbv501g.verkefni.persistence.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime screeningTime;

    public screening() {}

    public screening(LocalDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getScreeningTime() {
        return screeningTime;
    }

    public void setScreeningTime(LocalDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }
}
