package org.aykhan.dataprovider.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aykhan.dataprovider.dto.user.UserRequest;
import org.aykhan.dataprovider.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserListener {

  private final UserService userService;

  private final ObjectMapper objectMapper;

  @RabbitListener(queues = "${rabbitmq.queue}")
  public void onUserReceive(byte[] userRequest) throws IOException {
    UserRequest userRequest1 = objectMapper.readValue(userRequest, UserRequest.class);
    log.info("user request received {}", userRequest1);
    userService.add(userRequest1);
  }
}
