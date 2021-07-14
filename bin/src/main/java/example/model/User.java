package example.model;

import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User extends org.springframework.security.core.userdetails.User
implements UserDetails{
  String username;
  String password;
  Challenge challenge;
  private Boolean enabled=true;
  private Boolean accountNonExpired=true;
  private Boolean accountNonLocked=true;
  private boolean credentialsNonExpired=true;
  private Collection<? extends GrantedAuthority> authorities;

  public User(String username, String password,
    Collection<? extends GrantedAuthority> authorities, Challenge challenge) {
      super(username, password, true, true, true, true, authorities);

      this.username = username;
      this.password = password;
      this.authorities = authorities;
      this.challenge = challenge;
  }

  public void setUsername(String username) {
      this.username = username;
  }

  public void setPassword(String password) {
      this.password = password;
  }

  public Challenge getChallenge() {
      return challenge;
  }

  public void setChallenge(Challenge challenge) {
      this.challenge = challenge;
  }

  @Override
  public String getPassword() {
      return password;
  }

  @Override
  public String getUsername() {
      return username;
  }

  @Override
  public boolean isAccountNonExpired() {
      return accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
      return accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
      return credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
      return enabled;
  }
}
