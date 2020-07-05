package org.aykhan.loginservice.logger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aykhan.loginservice.dto.LogDTO;
import org.aykhan.loginservice.service.LogService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
public class LoggingFilter extends DispatcherServlet {

  private final LogService logService;

  @Override
  protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (!(request instanceof ContentCachingRequestWrapper)) {
      request = new ContentCachingRequestWrapper(request);
    }
    if (!(response instanceof ContentCachingResponseWrapper)) {
      response = new ContentCachingResponseWrapper(response);
    }
    HandlerExecutionChain handler = getHandler(request);

    try {
      super.doDispatch(request, response);
    } finally {
      log(request, response, handler);
      updateResponse(response);
    }
  }

  private void log(HttpServletRequest requestToCache, HttpServletResponse responseToCache, HandlerExecutionChain handler) {
    String pathInfo = requestToCache.getPathInfo();
    String method = requestToCache.getMethod();
    Map<String, String[]> requestParams = requestToCache
        .getParameterMap();
    String paramString = requestParams
        .entrySet()
        .stream()
        .map(entry -> String.format("{%s,%s}", entry.getKey(), Arrays.toString(entry.getValue())))
        .collect(Collectors.joining(",", "[", "]"));
    String requestBody = getRequestPayload(requestToCache);
    int status = responseToCache.getStatus();
    String responseBody = getResponsePayload(responseToCache);

    log.info("{} request to {}", method, pathInfo);
    log.debug("Params: {}", paramString);
    log.debug("Request body: {}", requestBody);
    log.info("Response status: {}", status);
    log.debug("Response body: {}", responseBody);

    sendLogs(method, pathInfo, requestParams, requestBody, status, responseBody);
  }

  private void sendLogs(String method, String pathInfo, Map<String, String[]> requestParams, String requestBody, int status, String responseBody) {
    Map<String, String> requestParamsMod = requestParams
        .entrySet()
        .stream()
        .map(entry -> new AbstractMap.SimpleEntry<String, String>(entry.getKey(), Arrays.toString(entry.getValue())))
        .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    LogDTO logDTO = LogDTO
        .builder()
        .method(method)
        .requestUrl(pathInfo)
        .requestParams(requestParamsMod)
        .requestBody(requestBody)
        .requestTime(LocalDateTime.now())
        .responseStatus(String.valueOf(status))
        .responseBody(responseBody)
        .source("data-provider")
        .build();
    logService.log(logDTO);
  }

  private String getResponsePayload(HttpServletResponse response) {
    ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
    if (wrapper != null) {
      byte[] buf = wrapper.getContentAsByteArray();
      if (buf.length > 0) {
        int length = buf.length;
        try {
          return new String(buf, 0, length, wrapper.getCharacterEncoding());
        } catch (UnsupportedEncodingException ex) {
          // NOOP
        }
      }
    }
    return "[unknown]";
  }

  private String getRequestPayload(HttpServletRequest request) {
    ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
    if (wrapper != null) {
      byte[] buf = wrapper.getContentAsByteArray();
      if (buf.length > 0) {
        int length = buf.length;
        try {
          return new String(buf, 0, length, wrapper.getCharacterEncoding());
        } catch (UnsupportedEncodingException ex) {
          // NOOP
        }
      }
    }
    return "[unknown]";
  }

  private void updateResponse(HttpServletResponse response) throws IOException {
    ContentCachingResponseWrapper responseWrapper =
        WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
    responseWrapper.copyBodyToResponse();
  }

}
