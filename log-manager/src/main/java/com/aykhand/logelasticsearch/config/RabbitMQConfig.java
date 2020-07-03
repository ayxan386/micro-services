package com.aykhand.logelasticsearch.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Value("${rabbitmq.exchange.logger}")
  public final String LOGGER_TOPIC_EXCHANGE = "logger-topic-exchange";
  @Value("${rabbitmq.queue}")
  private String queueName;
  @Value("${rabbitmq.routing.logging}")
  private String loggingRK;

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(LOGGER_TOPIC_EXCHANGE);
  }

  @Bean
  public Queue queue() {
    return new Queue(queueName, false);
  }

  @Bean
  public Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(loggingRK + ".#");
  }

}
