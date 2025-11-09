package is.hi.hbv501g.verkefni.services;

import is.hi.hbv501g.verkefni.controllers.dto.ProfileDto;
import is.hi.hbv501g.verkefni.persistence.entities.FriendRequest;

import java.util.List;

public interface FriendService {
    FriendRequest sendRequest(String fromEmail, String toEmail);
    FriendRequest acceptRequest(Long requestId, String actingEmail);
    FriendRequest rejectRequest(Long requestId, String actingEmail);
    List<String> listFriendsUsernames(String username);
    ProfileDto getProfile(String username, String viewerUsername);
}
