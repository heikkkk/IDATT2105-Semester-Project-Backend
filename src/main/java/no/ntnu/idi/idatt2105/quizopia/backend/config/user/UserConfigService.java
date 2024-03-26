package no.ntnu.idi.idatt2105.quizopia.backend.config.user;

import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.JDBCrepository.JDBCUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConfigService implements UserDetailsService {

  private final JDBCUserRepository userRepository;

  //TODO det her kan umulig funke
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
