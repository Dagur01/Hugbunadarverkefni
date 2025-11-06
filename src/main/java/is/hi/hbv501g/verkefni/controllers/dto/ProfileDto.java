package is.hi.hbv501g.verkefni.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProfileDto {
    private String username;
    private List<String> friends;
}
