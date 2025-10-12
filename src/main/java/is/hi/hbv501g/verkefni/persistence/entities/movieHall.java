package is.hi.hbv501g.verkefni.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movieHalls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class movieHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieHallId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private boolean nowShowing;
}
