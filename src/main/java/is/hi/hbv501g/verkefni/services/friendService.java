package is.hi.hbv501g.verkefni.services;

import is.hi.hbv501g.verkefni.controllers.dto.ProfileDto;
import is.hi.hbv501g.verkefni.persistence.entities.friendRequest;
import is.hi.hbv501g.verkefni.persistence.entities.movieInvitation;

import java.util.List;

public interface friendService {
    friendRequest sendRequest(String fromEmail, String toEmail);
    friendRequest acceptRequest(Long requestId, String actingEmail);
    friendRequest rejectRequest(Long requestId, String actingEmail);
    List<String> listFriendsUsernames(String username);
    ProfileDto getProfile(String username, String viewerUsername);

    movieInvitation inviteFriendToMovie(String inviterEmail, String inviteeEmail, Long movieId);
}
