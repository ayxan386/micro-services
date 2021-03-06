package org.aykhan.dataprovider.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRequest implements Serializable {
  private String name;
  private String surname;
  private String email;
  private String nickname;
  private String profilePicture;
}
