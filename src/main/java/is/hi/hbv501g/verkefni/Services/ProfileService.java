package is.hi.hbv501g.verkefni.Services;

import org.springframework.web.multipart.MultipartFile;

import is.hi.hbv501g.verkefni.Persistence.Entities.User;

public interface ProfileService {
    User get(String username);
    User updateEmail(String username, String newEmail);
    void updatePassword(String username, String password);
    void uploadPicture(String username, MultipartFile file);
}
