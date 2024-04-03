package no.ntnu.idi.idatt2105.quizopia.backend.config.user;

import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.user.JdbcUserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConfigService implements UserDetailsService {

  private final JdbcUserRepository userRepository;

  /**
   * Loads a user from the userRepository given the username.
   * The method then configures the user to be used by the API by mapping the retrived user with
   * the {@link UserConfig} class
   * @param username the name of the user to be loaded
   * @return the {@link UserDetails} mapped to the user
   * @throws UsernameNotFoundException if the user with the given username is not found.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return  userRepository
        .findByName(username)
        .map(user -> new UserConfig(user, userRepository))
        .orElseThrow(() -> new UsernameNotFoundException(
            "Username: " + username + " does not exist"
        ));
  }
}
