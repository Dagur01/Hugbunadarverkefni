package is.hi.hbv501g.verkefni.persistence.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "movie_invitations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class movieInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inviter_id")
    private user inviter;

    @ManyToOne(optional = false)
    @JoinColumn(name = "invitee_id")
    private user invitee;

    private Long movieId;

    private String status; // "SENT", "ACCEPTED", "DECLINED"

    private LocalDateTime createdAt;
}
