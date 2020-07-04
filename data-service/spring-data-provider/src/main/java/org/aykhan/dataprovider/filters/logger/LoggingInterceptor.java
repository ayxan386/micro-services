package org.aykhan.dataprovider.filters.logger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aykhan.dataprovider.service.LogService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class LoggingInterceptor {

  @Component
  @RequiredArgsConstructor
  @Slf4j
  private static class RequestFilter extends OncePerRequestFilter {

    private final LogService logService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      if (isAsyncDispatch(request)) {
        filterChain.doFilter(request, response);
      } else {
        try {
          filterChain.doFilter(request, response);
        } finally {
          ContentCachingResponseWrapper responseWrapper = response instanceof ContentCachingResponseWrapper
              ? (ContentCachingResponseWrapper) response
              : new ContentCachingResponseWrapper(response);
          StringBuffer requestURL = request.getRequestURL();
          log.info("Request to: {}", requestURL);
          String method = request.getMethod();
          log.info("Request method: {}", method);
          Map<String, String[]> parameterMap = request.getParameterMap();
          String params = parameterMap
              .entrySet()
              .stream()
              .map(entry -> String.format("<%s>:<%s>", entry.getKey(), Arrays.toString(entry.getValue())))
              .collect(Collectors.joining(",", "{", "}"));
          log.info("Request params: {}", params);
          String requestBody = extractRequestBody(request);
          log.info("Request body: {}", requestBody);

          int responseStatus = responseWrapper.getStatus();
          log.info("Response status: {}", responseStatus);
          String responseBody = extractResponseBody(responseWrapper);
          log.info("Response body: {}", responseBody);
        }
      }
    }

    private String extractRequestBody(HttpServletRequest request) throws IOException {
      return request.getContentLength() > 0
          ? request.getReader().lines().collect(Collectors.joining("\n"))
          : "no body";
    }


    /**
     * Spring Web filter for logging request and response.
     *
     * @author Hidetake Iwata
     * @see org.springframework.web.filter.AbstractRequestLoggingFilter
     * @see ContentCachingRequestWrapper
     * @see ContentCachingResponseWrapper
     */
    private String extractResponseBody(ContentCachingResponseWrapper response) {
      byte[] content = response.getContentAsByteArray();
      if (content.length > 0) {
        return logContent(content, response.getContentType(), response.getCharacterEncoding());
      }
      return "no body";
    }

    private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(
        MediaType.valueOf("text/*"),
        MediaType.APPLICATION_FORM_URLENCODED,
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML,
        MediaType.valueOf("application/*+json"),
        MediaType.valueOf("application/*+xml"),
        MediaType.MULTIPART_FORM_DATA
    );

    private String logContent(byte[] content, String contentType, String contentEncoding) {
      MediaType mediaType = MediaType.valueOf(contentType);
      boolean visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
      if (visible) {
        try {
          return new String(content, contentEncoding);
        } catch (UnsupportedEncodingException e) {
          log.info("[{} bytes content]", content.length);
        }
      } else {
        log.info("[{} bytes content]", content.length);
      }
      return "no body";
    }
  }
}
