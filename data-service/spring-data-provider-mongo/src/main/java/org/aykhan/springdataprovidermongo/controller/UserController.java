package org.aykhan.springdataprovidermongo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aykhan.springdataprovidermongo.dto.RestResponse;
import org.aykhan.springdataprovidermongo.dto.user.UserRequest;
import org.aykhan.springdataprovidermongo.dto.user.UserResponse;
import org.aykhan.springdataprovidermongo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    public static final String MESSAGE = "SUCCESS";
    private final UserService userService;

    @GetMapping
    public ResponseEntity<RestResponse<UserResponse>> getUser(@RequestBody UserRequest userRequest) {
        log.info("user {} get request", userRequest);
        return ResponseEntity.ok(
                new RestResponse<>(userService.get(userRequest), MESSAGE)
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
}
