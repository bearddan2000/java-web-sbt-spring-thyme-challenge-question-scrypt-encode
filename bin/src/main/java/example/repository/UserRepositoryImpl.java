package example.repository;

import example.model.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
  	public example.config.InMemoryManager inMemoryUserDetailsManager;

  	@Autowired
  	public PasswordEncoder passwordEncoder;

    @Override
    public UserDetails findByName(String name){
      try {
        return inMemoryUserDetailsManager.loadUserByUsername(name);
      } catch(Exception e) {}
        return null;
    }

    @Override
    public User resetPassword(String name, String newPassword){
      User user = null;
      boolean userExists = inMemoryUserDetailsManager.userExists(name);
      if (userExists){
        user = (User)inMemoryUserDetailsManager.loadUserByUsername(name);
        String password = passwordEncoder.encode(newPassword);
        final example.model.Challenge c = user.getChallenge();
        user = new User(name, password, this.createAuthorities("USER"), c);
        inMemoryUserDetailsManager.deleteUser(name);
      }
      return user;
    }

    @Override
    public void save(User user){
      this.createUser(user);
    }

    @Override
    public ArrayList<GrantedAuthority> createAuthorities(String role)
    {
      ArrayList<GrantedAuthority> grantedAuthoritiesList= new ArrayList<>();
      grantedAuthoritiesList.add(new SimpleGrantedAuthority("ROLE_" + role));
      return grantedAuthoritiesList;
    }

    private void createUser(User user){
      String name = user.getUsername();
      String password = passwordEncoder.encode(user.getPassword());
      example.model.Challenge c = user.getChallenge();
      if (c == null) {
        c = new example.model.Challenge("0", "1900");
      }
      inMemoryUserDetailsManager.createUser(new User(name, password, this.createAuthorities("USER"), c));
    }
}
