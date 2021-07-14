package example.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * This class exists because Spring default InMemoryUserDetailsManager doesn't return
 * instances of types it's given. It only returns instances of UserDetails which isn't
 * good if you want to work with your own User class.
 *
 * @see org.springframework.security.provisioning.InMemoryUserDetailsManager
 */
public class InMemoryManager implements UserDetailsService {

    private final Map<String, example.model.User> users = new HashMap<>();

    public void addUserList(List<example.model.User> userDetailsList){
      for (example.model.User u : userDetailsList) {
        createUser(u);
      }
    }

    //@Override
    public void createUser(UserDetails user) {
        Assert.isTrue(!userExists(user.getUsername()));

        users.put(user.getUsername().toLowerCase(), (example.model.User) user);
    }

    //@Override
    public void updateUser(UserDetails user) {
        Assert.isTrue(userExists(user.getUsername()));

        users.put(user.getUsername().toLowerCase(), (example.model.User) user);
    }

    //@Override
    public void deleteUser(String username) {
        users.remove(username.toLowerCase());
    }

    //@Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException();
    }

    //@Override
    public boolean userExists(String username) {
        return users.containsKey(username.toLowerCase());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        example.model.User user = users.get(username.toLowerCase());

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }
}
