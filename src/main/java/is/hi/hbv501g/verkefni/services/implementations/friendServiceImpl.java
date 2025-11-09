package is.hi.hbv501g.verkefni.services.implementations;

import is.hi.hbv501g.verkefni.controllers.dto.ProfileDto;
import is.hi.hbv501g.verkefni.persistence.entities.FriendRequest;
import is.hi.hbv501g.verkefni.persistence.entities.User;
import is.hi.hbv501g.verkefni.persistence.repositories.FriendRequestRepository;
import is.hi.hbv501g.verkefni.persistence.repositories.UserRepository;
import is.hi.hbv501g.verkefni.services.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;

    @Override
    public FriendRequest sendRequest(String fromEmail, String toEmail) {
        if (fromEmail.equalsIgnoreCase(toEmail)) {
            throw new IllegalArgumentException("Cannot send friend request to yourself");
        }
        User from = userRepository.findByEmail(fromEmail)
                .orElseThrow(() -> new NoSuchElementException("Sender not found"));
        User to = userRepository.findByEmail(toEmail)
                .orElseThrow(() -> new NoSuchElementException("Target user not found"));

        // If already friends (accepted request exists)
        Optional<FriendRequest> existingAccepted = friendRequestRepository.findByFromUserAndToUserAndStatus(from, to, "ACCEPTED")
                .or(() -> friendRequestRepository.findByFromUserAndToUserAndStatus(to, from, "ACCEPTED"));
        if (existingAccepted.isPresent()) {
            throw new IllegalStateException("Already friends");
        }

        // If pending request exists in either direction, return/throw
        Optional<FriendRequest> existingPending1 = friendRequestRepository.findByFromUserAndToUserAndStatus(from, to, "PENDING");
        Optional<FriendRequest> existingPending2 = friendRequestRepository.findByFromUserAndToUserAndStatus(to, from, "PENDING");
        if (existingPending1.isPresent() || existingPending2.isPresent()) {
            throw new IllegalStateException("Friend request already pending");
        }

        FriendRequest fr = FriendRequest.builder()
                .fromUser(from)
                .toUser(to)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        return friendRequestRepository.save(fr);
    }

    @Override
    public FriendRequest acceptRequest(Long requestId, String actingEmail) {
        FriendRequest fr = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("Friend request not found"));
        if (!fr.getToUser().getEmail().equalsIgnoreCase(actingEmail)) {
            throw new SecurityException("Only the recipient can accept the request");
        }
        if (!"PENDING".equals(fr.getStatus())) {
            throw new IllegalStateException("Request is not pending");
        }
        fr.setStatus("ACCEPTED");
        return friendRequestRepository.save(fr);
    }

    @Override
    public FriendRequest rejectRequest(Long requestId, String actingEmail) {
        FriendRequest fr = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("Friend request not found"));
        if (!fr.getToUser().getEmail().equalsIgnoreCase(actingEmail)) {
            throw new SecurityException("Only the recipient can reject the request");
        }
        if (!"PENDING".equals(fr.getStatus())) {
            throw new IllegalStateException("Request is not pending");
        }
        fr.setStatus("REJECTED");
        return friendRequestRepository.save(fr);
    }


    @Override
    public List<String> listFriendsUsernames(String email) {
        User u = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        List<FriendRequest> accepted = friendRequestRepository.findByFromUserOrToUserAndStatus(u, u, "ACCEPTED");
        return accepted.stream()
                .map(fr -> fr.getFromUser().getUserId() == (u.getUserId()) ? fr.getToUser().getUsername() : fr.getFromUser().getUsername())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public ProfileDto getProfile(String username, String viewerUsername) {
        User u = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        List<String> friends = listFriendsUsernames(username);

        ProfileDto dto = new ProfileDto(u.getUsername(), friends);

        return dto;
    }
}
