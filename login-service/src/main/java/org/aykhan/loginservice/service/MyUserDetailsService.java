package org.aykhan.loginservice.service;

import lombok.AllArgsConstructor;
import org.aykhan.loginservice.repository.UserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

  private final UserDetailsRepository userDetailsRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userDetailsRepository
        .findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found"));
  }
}
