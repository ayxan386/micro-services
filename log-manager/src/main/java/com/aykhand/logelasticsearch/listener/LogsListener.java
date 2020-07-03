package com.aykhand.logelasticsearch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogsListener {
  @RabbitListener(queues = "${rabbitmq.queue}")
  public void onMessageReceived(byte[] rawMessage) {
    String messageStr = new String(rawMessage);
    log.info("received message {}", messageStr);
  }
}
