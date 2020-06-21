package org.aykhan.dataprovider.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aykhan.dataprovider.config.UserPrincipialInjectorConfig;
import org.aykhan.dataprovider.dto.RestResponse;
import org.aykhan.dataprovider.dto.comments.CommentRequest;
import org.aykhan.dataprovider.dto.comments.CommentResponse;
import org.aykhan.dataprovider.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

  private static final String MESSAGE = "success";
  private final CommentService commentService;
  private final UserPrincipialInjectorConfig injectorConfig;

  @PostMapping
  public ResponseEntity<RestResponse<CommentResponse>> makeComment(@RequestBody CommentRequest commentRequest) {
    log.info("user {} adding comment", injectorConfig.getUser().getUsername());
    return ResponseEntity.ok(
        RestResponse
            .<CommentResponse>builder()
            .data(commentService.add(commentRequest))
            .message(MESSAGE)
            .build());
  }

  @DeleteMapping
  public ResponseEntity<RestResponse<String>> deleteComment(@RequestBody CommentRequest request) {
    log.info("user {} deleting comment", injectorConfig.getUser().getUsername());
    return ResponseEntity.ok(
        RestResponse
            .<String>builder()
            .data(commentService.delete(request))
            .message(MESSAGE)
            .build()
    );
  }
}
