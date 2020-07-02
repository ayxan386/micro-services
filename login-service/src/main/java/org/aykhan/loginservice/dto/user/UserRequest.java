package org.aykhan.loginservice.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRequest implements Serializable {
  private String name;
  private String surname;
  private String email;
  private String nickname;
  private String profilePicture;
}
