package example.service;

import example.model.User;
import example.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails findByName(String username){
      return userRepository.findByName(username);
    }

    @Override
    public void save(User user) {
        user.setPassword(user.getPassword());
        userRepository.save(user);
        System.out.println("[LOG] user saved");
    }

    @Override
    public User resetPassword(String username, String newPassword) {
      User user = userRepository.resetPassword(username, newPassword);
      if (user == null) {
        System.out.println("[LOG] user not found");
      } else {
        userRepository.save(user);
        System.out.println("[LOG] user password changed");
      }
      return user;
    }

    @Override
    public ArrayList<GrantedAuthority> createAuthorities(String role)
    {
      return userRepository.createAuthorities(role);
    }
}
