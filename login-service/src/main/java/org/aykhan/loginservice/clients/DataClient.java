package org.aykhan.loginservice.clients;

import org.aykhan.loginservice.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@FeignClient(
    url = "${SERVER.IP}:5002/api",
    name = "data-provider"
)
public interface DataClient {
  @PostMapping("/user")
  String saveUser(
      @RequestHeader(value = AUTHORIZATION) String token,
      @RequestBody UserDTO userDetails);
}
