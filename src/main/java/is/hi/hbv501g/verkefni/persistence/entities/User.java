package is.hi.hbv501g.verkefni.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    public enum Role {USER, ADMIN}




}
