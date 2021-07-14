package example.service;

import example.model.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;

public interface UserService {
  void save(User user);
  User resetPassword(String username, String newPassword);
  UserDetails findByName(String username);
  ArrayList<GrantedAuthority> createAuthorities(String role);
}
