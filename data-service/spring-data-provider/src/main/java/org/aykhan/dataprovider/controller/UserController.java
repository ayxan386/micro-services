package org.aykhan.dataprovider.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aykhan.dataprovider.config.UserPrincipialInjectorConfig;
import org.aykhan.dataprovider.dto.RestResponse;
import org.aykhan.dataprovider.dto.user.UserRequest;
import org.aykhan.dataprovider.dto.user.UserResponse;
import org.aykhan.dataprovider.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  public static final String MESSAGE = "SUCCESS";
  private final UserService userService;
  private final UserPrincipialInjectorConfig injectorConfig;

  @GetMapping(value = "/me")
  public ResponseEntity<RestResponse<UserResponse>> getMe() {
    String username = injectorConfig.getUser().getUsername();
    log.info("user {} get request", username);
    return ResponseEntity.ok(
        new RestResponse<>(userService.get(UserRequest.builder().nickname(username).build()), MESSAGE)
    );
  }

  @GetMapping
  public ResponseEntity<RestResponse<UserResponse>> getUser(@RequestParam(name = "username") String username) {
    log.info("user {} get request", username);
    return ResponseEntity.ok(
        new RestResponse<>(userService.get(UserRequest.builder().nickname(username).build()), MESSAGE)
    );
  }

  @PostMapping
  public ResponseEntity<RestResponse<UserResponse>> addUser(@RequestBody UserRequest userRequest) {
    log.info("user {} post request", userRequest);
    return ResponseEntity.ok(
        new RestResponse<>(userService.add(userRequest), MESSAGE)
    );
  }

  @PutMapping
  public ResponseEntity<RestResponse<UserResponse>> updateUser(@RequestBody UserRequest userRequest) {
    log.info("user {} put request", userRequest);
    return ResponseEntity.ok(
        new RestResponse<>(userService.update(userRequest), MESSAGE)
    );
  }

  @DeleteMapping
  public ResponseEntity<RestResponse<String>> deleteUser(@RequestBody UserRequest userRequest) {
    log.info("user {} delete request", userRequest);
    return ResponseEntity.ok(
        new RestResponse<>(userService.delete(userRequest), MESSAGE)
    );
  }

  @DeleteMapping("clear")
  public ResponseEntity<RestResponse<String>> clear(@RequestBody UserRequest userRequest) {
    log.info("clear the cache");
    userService.clearCache(userRequest);
    return ResponseEntity.ok(
        new RestResponse<>("success", MESSAGE)
    );
  }

  @PostMapping("/save")
  public ResponseEntity<RestResponse<String>> changeProfilePicture(@RequestParam(name = "file") MultipartFile file) {
    return ResponseEntity.ok(
        RestResponse.<String>builder()
            .message("success")
            .data(userService.savePicture(file))
            .build()
    );
  }
}
