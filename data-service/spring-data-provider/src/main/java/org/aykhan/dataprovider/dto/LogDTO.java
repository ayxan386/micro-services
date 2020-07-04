package org.aykhan.dataprovider.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogDTO {
  private String requestUrl;
  private String requestBody;
  private Map<String, String> requestParams;
  private String method;
  private String responseBody;
  private String responseStatus;
  private String source;
  private LocalDateTime requestTime;
}
