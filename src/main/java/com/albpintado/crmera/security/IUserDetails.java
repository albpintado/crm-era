package com.albpintado.crmera.security;

import com.albpintado.crmera.user.User;
import com.albpintado.crmera.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
Service in charge of retrieving user from the repository
that matches the email sent.
 */
@Service
public class IUserDetails implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = this.userRepository.findOneByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("The user with email " + email + " does not exist."));
    return new UserDetailsImpl(user);
  }
}
