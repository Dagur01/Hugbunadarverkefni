package is.hi.hbv501g.verkefni.persistence.entities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class User {
    private String username;
    private String email;
    private String password;
    private boolean isAdmin;
    private String profileImagePath;

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String p) {
        this.profileImagePath = p;
    }

}
