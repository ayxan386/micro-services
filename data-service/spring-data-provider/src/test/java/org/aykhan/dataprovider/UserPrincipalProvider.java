package org.aykhan.dataprovider;

import org.aykhan.dataprovider.security.UserPrincipal;

public class UserPrincipalProvider {
  public static final UserPrincipal USER = UserPrincipal
      .builder()
      .username("aykhan")
      .build();
}
