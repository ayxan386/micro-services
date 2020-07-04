package com.aykhand.logelasticsearch.listener;

import com.aykhand.logelasticsearch.model.LogModel;
import com.aykhand.logelasticsearch.repository.LogsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class LogsListener {
  private final LogsRepository logsRepository;
  private final ObjectMapper objectMapper;

  @RabbitListener(queues = "${rabbitmq.logque}")
  public void onMessageReceived(byte[] rawMessage) {
    try {
      LogModel logModel = objectMapper.readValue(rawMessage, LogModel.class);
      log.debug("received message {}", logModel);
      logsRepository.save(logModel);
    } catch (IOException e) {
      log.error("Error occurred while processing message");
    }
  }
}
