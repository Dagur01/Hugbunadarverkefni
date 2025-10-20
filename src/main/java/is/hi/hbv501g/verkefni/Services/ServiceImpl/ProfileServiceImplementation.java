package is.hi.hbv501g.verkefni.Services.ServiceImpl;

import org.springframework.web.multipart.MultipartFile;

import is.hi.hbv501g.verkefni.Persistence.Entities.User;
import is.hi.hbv501g.verkefni.Services.ProfileService;

public class ProfileServiceImplementation implements ProfileService{

    @Override
    public User get(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public User updateEmail(String username, String newEmail) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateEmail'");
    }

    @Override
    public void updatePassword(String username, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePassword'");
    }

    @Override
    public void uploadPicture(String username, MultipartFile file) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uploadPicture'");
    }
}
