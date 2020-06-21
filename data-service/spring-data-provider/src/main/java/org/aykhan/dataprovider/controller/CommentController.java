package org.aykhan.dataprovider.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aykhan.dataprovider.dto.RestResponse;
import org.aykhan.dataprovider.dto.comments.CommentRequest;
import org.aykhan.dataprovider.dto.comments.CommentResponse;
import org.aykhan.dataprovider.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

  private static final String MESSAGE = "success";
  private final CommentService commentService;

  @PostMapping
  public ResponseEntity<RestResponse<CommentResponse>> makeComment(@RequestBody CommentRequest commentRequest) {
    return ResponseEntity.ok(
        RestResponse
            .<CommentResponse>builder()
            .data(commentService.add(commentRequest))
            .message(MESSAGE)
            .build());
  }
}
