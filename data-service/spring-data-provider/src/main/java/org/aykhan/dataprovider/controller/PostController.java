package org.aykhan.dataprovider.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aykhan.dataprovider.dto.RestResponse;
import org.aykhan.dataprovider.dto.post.PostRequest;
import org.aykhan.dataprovider.dto.post.PostResponse;
import org.aykhan.dataprovider.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
@Slf4j
public class PostController {

  public static final String MESSAGE = "SUCCESS";
  private final PostService postService;

  @GetMapping
  public ResponseEntity<RestResponse<PostResponse>> getPost(@RequestBody PostRequest postRequest) {
    log.info("getting post with {} id", postRequest.getId());
    return ResponseEntity.ok(
        new RestResponse<>(postService.get(postRequest), MESSAGE)
    );
  }

  @PostMapping
  public ResponseEntity<RestResponse<PostResponse>> addPost(@RequestBody PostRequest postRequest) {
    log.info("adding new post");
    return ResponseEntity.ok(
        new RestResponse<>(postService.add(postRequest), MESSAGE)
    );
  }

  @PutMapping
  public ResponseEntity<RestResponse<PostResponse>> updatePost(@RequestBody PostRequest postRequest) {
    log.info("updating existing post with {} id", postRequest.getId());
    return ResponseEntity.ok(
        new RestResponse<>(postService.update(postRequest), MESSAGE)
    );
  }

  @DeleteMapping
  public ResponseEntity<RestResponse<String>> deletePost(@RequestBody PostRequest postRequest) {
    log.info("deleting post with {} id", postRequest.getId());
    return ResponseEntity.ok(
        new RestResponse<>(postService.delete(postRequest), MESSAGE)
    );
  }

  @GetMapping("/all")
  public ResponseEntity<RestResponse<List<PostResponse>>> getAll() {
    log.info("getting all posts for this user");
    return ResponseEntity.ok(
        new RestResponse<>(postService.getAll(), MESSAGE)
    );
  }
}
