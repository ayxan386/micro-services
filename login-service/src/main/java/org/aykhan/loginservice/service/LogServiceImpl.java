package org.aykhan.loginservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aykhan.loginservice.dto.LogDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogServiceImpl implements LogService {

  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;
  @Value("${rabbitmq.routing.logging}")
  private String loggingRK;
  @Value("${rabbitmq.exchange}")
  private String exchangeName;

  @Override
  public void log(LogDTO logDTO) {
    try {
      logDTO.setSource("data-provider");
      byte[] message = objectMapper.writeValueAsBytes(logDTO);
      rabbitTemplate
          .convertAndSend(exchangeName, loggingRK + ".add", message);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
