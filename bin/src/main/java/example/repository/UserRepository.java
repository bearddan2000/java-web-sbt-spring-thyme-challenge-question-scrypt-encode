package example.repository;

import example.model.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository("userRepository")
public interface UserRepository {
    UserDetails findByName(String name);
    User resetPassword(String name, String newPassword);
    void save(User user);
    ArrayList<GrantedAuthority> createAuthorities(String role);
}
