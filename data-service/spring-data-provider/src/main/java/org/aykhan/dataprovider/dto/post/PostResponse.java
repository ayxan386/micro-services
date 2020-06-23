package org.aykhan.dataprovider.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aykhan.dataprovider.dto.comments.CommentResponse;
import org.aykhan.dataprovider.dto.user.UserResponse;

import java.time.LocalDateTime;
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
  @JsonFormat(pattern = "HH:mm dd/MM/yyyy")
  private LocalDateTime updatedOn;
}
