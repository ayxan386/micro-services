package org.aykhan.loginservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aykhan.loginservice.dto.JwtResponse;
import org.aykhan.loginservice.dto.LoginDTO;
import org.aykhan.loginservice.dto.RegisterRequest;
import org.aykhan.loginservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@RequestBody LoginDTO loginDTO) {
    return ResponseEntity.ok(authService.login(loginDTO));
  }

  @PostMapping("/register")
  public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest registerRequest) {
    log.info("Registering new user");
    return ResponseEntity.ok(authService.register(registerRequest));
  }
}
