package is.hi.hbv501g.verkefni.services.implementations;

import is.hi.hbv501g.verkefni.controllers.dto.ProfileDto;
import is.hi.hbv501g.verkefni.persistence.entities.friendRequest;
import is.hi.hbv501g.verkefni.persistence.entities.user;
import is.hi.hbv501g.verkefni.persistence.repositories.friendRequestRepository;
import is.hi.hbv501g.verkefni.persistence.repositories.userRepository;
import is.hi.hbv501g.verkefni.services.friendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class friendServiceImpl implements friendService {

    private final userRepository userRepository;
    private final friendRequestRepository friendRequestRepository;

    @Override
    public friendRequest sendRequest(String fromEmail, String toEmail) {
        if (fromEmail.equalsIgnoreCase(toEmail)) {
            throw new IllegalArgumentException("Cannot send friend request to yourself");
        }
        user from = userRepository.findByEmail(fromEmail)
                .orElseThrow(() -> new NoSuchElementException("Sender not found"));
        user to = userRepository.findByEmail(toEmail)
                .orElseThrow(() -> new NoSuchElementException("Target user not found"));

        // If already friends (accepted request exists)
        Optional<friendRequest> existingAccepted = friendRequestRepository.findByFromUserAndToUserAndStatus(from, to, "ACCEPTED")
                .or(() -> friendRequestRepository.findByFromUserAndToUserAndStatus(to, from, "ACCEPTED"));
        if (existingAccepted.isPresent()) {
            throw new IllegalStateException("Already friends");
        }

        // If pending request exists in either direction, return/throw
        Optional<friendRequest> existingPending1 = friendRequestRepository.findByFromUserAndToUserAndStatus(from, to, "PENDING");
        Optional<friendRequest> existingPending2 = friendRequestRepository.findByFromUserAndToUserAndStatus(to, from, "PENDING");
        if (existingPending1.isPresent() || existingPending2.isPresent()) {
            throw new IllegalStateException("Friend request already pending");
        }

        friendRequest fr = friendRequest.builder()
                .fromUser(from)
                .toUser(to)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        return friendRequestRepository.save(fr);
    }

    @Override
    public friendRequest acceptRequest(Long requestId, String actingEmail) {
        friendRequest fr = friendRequestRepository.findById(requestId)
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
    public friendRequest rejectRequest(Long requestId, String actingEmail) {
        friendRequest fr = friendRequestRepository.findById(requestId)
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
        user u = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        List<friendRequest> accepted = friendRequestRepository.findByFromUserOrToUserAndStatus(u, u, "ACCEPTED");
        return accepted.stream()
                .map(fr -> fr.getFromUser().getUserId() == (u.getUserId()) ? fr.getToUser().getUsername() : fr.getFromUser().getUsername())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public ProfileDto getProfile(String email, String viewerEmail) {
        user target = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        // Ef enginn skoðandi (ekki innskráður)
        if (viewerEmail == null) {
            return ProfileDto.builder()
                    .email(target.getEmail())
                    .username(target.getUsername())
                    .isFriend(false)
                    .friends(List.of()) // ekkert vinayfirlit sést
                    .profilePictureBase64(null)
                    .build();
        }

        // Finna þann sem skoðar
        user viewer = userRepository.findByEmail(viewerEmail)
                .orElseThrow(() -> new NoSuchElementException("Viewer not found"));

        boolean isSelf = viewer.getUserId() == target.getUserId();
        boolean isFriend = areFriends(viewer, target);

        List<String> friends = listFriendsUsernames(target.getEmail());

        return ProfileDto.builder()
                .username(target.getUsername())
                .email(target.getEmail()) // aðeins eigandi sér email sitt
                .isFriend(isFriend)
                .friends(friends)
                .profilePictureBase64(target.getProfilePicture() != null
                        ? java.util.Base64.getEncoder().encodeToString(target.getProfilePicture())
                        : null)
                .build();
    }

    // hjálparaðferð til að athuga vináttu
    private boolean areFriends(user a, user b) {
        return friendRequestRepository.findByFromUserAndToUserAndStatus(a, b, "ACCEPTED").isPresent()
                || friendRequestRepository.findByFromUserAndToUserAndStatus(b, a, "ACCEPTED").isPresent();
    }


}
