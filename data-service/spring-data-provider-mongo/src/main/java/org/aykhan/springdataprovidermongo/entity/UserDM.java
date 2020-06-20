package org.aykhan.springdataprovidermongo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Document(collection = "users")
public class UserDM implements Serializable {
    @Id
    private String id;
    private String name;
    private String surname;
    private String email;
    @Indexed(unique = true)
    private String nickname;
    private String profilePicture;
}
