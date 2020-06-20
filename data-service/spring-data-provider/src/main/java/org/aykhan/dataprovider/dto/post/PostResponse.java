package org.aykhan.dataprovider.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aykhan.dataprovider.dto.comments.CommentResponse;
import org.aykhan.dataprovider.dto.user.UserResponse;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
  private Long id;
  private String title;
  private String body;
  private UserResponse author;
  private List<CommentResponse> comments;
}
