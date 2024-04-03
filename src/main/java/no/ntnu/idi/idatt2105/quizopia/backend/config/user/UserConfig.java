package no.ntnu.idi.idatt2105.quizopia.backend.config.user;

import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.model.User;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.user.JdbcUserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserConfig class implements the UserDetails interface
 * to provide user authentication and authorization details.
 */
@RequiredArgsConstructor
public class UserConfig implements UserDetails {
  private final User user;
  private final JdbcUserRepository userRepository;

  /**
   * Method for finding the collection of authorites a user has.
   * This methods searches the database to find the role associated with a user.
   * Each role has the prefix {ROLE} followed by the role type. For example: ROLE_USER
   * @return the role of the user.
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(
        userRepository.findRoleByName(user.getUsername()).get()));
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
