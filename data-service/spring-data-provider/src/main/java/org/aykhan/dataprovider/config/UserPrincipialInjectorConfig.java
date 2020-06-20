package org.aykhan.dataprovider.config;

import org.aykhan.dataprovider.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserPrincipialInjectorConfig {
  public UserPrincipal getUser() {
    return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
