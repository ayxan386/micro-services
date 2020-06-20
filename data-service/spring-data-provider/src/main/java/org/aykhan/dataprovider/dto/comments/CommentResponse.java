package org.aykhan.dataprovider.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
  private Long postId;
  private String body;
}
