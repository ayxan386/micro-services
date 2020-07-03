package com.aykhand.logelasticsearch.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Document(indexName = "logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class LogModel {
  @Id
  private String id;
  private String message;
  private String source;
  @CreatedDate
  private LocalDateTime createdAt;
}
