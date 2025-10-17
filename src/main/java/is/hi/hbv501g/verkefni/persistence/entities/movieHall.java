package is.hi.hbv501g.verkefni.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "movieHall", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<seat> seats;

}
