package org.aykhan.loginservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static final String AUTH_TOPIC_EXCHANGE = "auth-topic-exchange";
  @Value("${rabbitmq.queue}")
  private String queueName;


  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(AUTH_TOPIC_EXCHANGE);
  }

  @Bean
  public Queue queue() {
    return new Queue(queueName, false);
  }

}
