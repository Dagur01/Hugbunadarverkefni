package is.hi.hbv501g.verkefni.services;

import is.hi.hbv501g.verkefni.persistence.entities.User;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    User get(String username);

    User updateEmail(String username, String newEmail);

    void updatePassword(String username, String password);

    void uploadPicture(String username, MultipartFile file);
}
