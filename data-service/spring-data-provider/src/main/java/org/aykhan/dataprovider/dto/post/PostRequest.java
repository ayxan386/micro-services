package org.aykhan.dataprovider.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
  private Long id;
  private String body;
  private String title;
}
