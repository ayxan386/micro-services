package org.aykhan.loginservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aykhan.loginservice.clients.DataClient;
import org.aykhan.loginservice.dto.JwtResponse;
import org.aykhan.loginservice.dto.LoginDTO;
import org.aykhan.loginservice.dto.RegisterRequest;
import org.aykhan.loginservice.dto.user.UserDTO;
import org.aykhan.loginservice.entity.MyUserDetails;
import org.aykhan.loginservice.exception.BadAuthException;
import org.aykhan.loginservice.exception.BadRequest;
import org.aykhan.loginservice.exception.LoginFailedException;
import org.aykhan.loginservice.exception.UserAlreadyExists;
import org.aykhan.loginservice.mapper.UserMapper;
import org.aykhan.loginservice.repository.UserDetailsRepository;
import org.aykhan.loginservice.security.jwt.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
  private final PasswordEncoder encoder;
  private final UserMapper mapper;
  private final UserDetailsRepository userDetailsRepository;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authManager;
  private final DataClient dataClient;

  public JwtResponse login(LoginDTO loginDTO) {
    return generateTokenFromEntity(
        Optional.of(loginDTO)
            .map(mapper::loginRequestToEntity)
            .orElseThrow(LoginFailedException::new)
    );
  }

  public JwtResponse register(RegisterRequest registerRequest) {
    JwtResponse token = generateTokenFromEntity(Optional.of(registerRequest)
        .map(mapper::registerRequestToEntity)
        .filter(req -> !userDetailsRepository.existsByUsername(req.getUsername()))
        .map(req -> {
          req.setPassword(encoder.encode(req.getPassword()));
          userDetailsRepository.save(req);
          return req;
        })
        .map(req -> mapper.registerRequestToEntity(registerRequest))
        .orElseThrow(UserAlreadyExists::new)
    );

    try {
      dataClient.saveUser("Bearer " + token.getToken(), UserDTO.builder().nickname(registerRequest.getUsername()).build());
    } catch (Exception e) {
      throw new BadRequest(e.getMessage());
    }

    return token;
  }

  private JwtResponse generateTokenFromEntity(MyUserDetails par) {
    return Optional.ofNullable(par)
        .map(entity -> {
          try {
            authManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        entity.getUsername(), entity.getPassword())
                );
          } catch (Exception e) {
            return null;
          }
          return entity;
        })
        .map(jwtUtil::generateToken)
        .map(token -> JwtResponse
            .builder()
            .token(token)
            .build())
        .orElseThrow(BadAuthException::new);
  }
}
