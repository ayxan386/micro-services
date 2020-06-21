package org.aykhan.dataprovider.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aykhan.dataprovider.dto.user.UserResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
  private Long postId;
  private Long commentId;
  private String body;
  private UserResponse author;
}
