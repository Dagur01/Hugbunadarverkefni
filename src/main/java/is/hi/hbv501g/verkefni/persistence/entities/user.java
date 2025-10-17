package is.hi.hbv501g.verkefni.persistence.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class user {

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

    @Column(name = "profile_picture", columnDefinition = "bytea")
    private byte[] profilePicture;

    @Column(name = "profile_picture_content_type")
    private String profilePictureContentType;

    @Column
    private String username;


    public enum Role {USER, ADMIN}




}
