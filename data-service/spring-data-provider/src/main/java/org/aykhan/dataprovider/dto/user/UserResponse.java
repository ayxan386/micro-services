package org.aykhan.dataprovider.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@RedisHash
public class UserResponse implements Serializable {
    private String name;
    private String surname;
    private String email;
    private String nickname;
    private String profilePicture;
}
