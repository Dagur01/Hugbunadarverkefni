package is.hi.hbv501g.verkefni.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDto {
    private String username;
    private String email;
    private boolean isFriend;
    private List<String> friends;
    private String profilePictureBase64;
}
