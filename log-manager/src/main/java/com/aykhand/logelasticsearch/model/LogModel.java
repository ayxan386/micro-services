package com.aykhand.logelasticsearch.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Map;

@Document(indexName = "logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class LogModel {
  @Id
  private String id;
  private String requestUrl;
  private String requestBody;
  private Map<String, String> requestParams;
  private String method;
  private String responseBody;
  private String responseStatus;
  private String source;
  private String requestTime;
}
