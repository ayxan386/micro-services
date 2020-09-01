package org.aykhan.dataprovider.config;

import org.aykhan.dataprovider.dto.user.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


@Configuration
public class RedisConfig {
  @Value("${spring.redis.host}")
  private String host;
  @Value("${spring.redis.port}")
  private int port;
  private static final long CACHE_TIME_TO_LIVE = 30L;

  @Bean
  protected JedisConnectionFactory jedisConnectionFactory() {
    JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
    connectionFactory.getStandaloneConfiguration().setHostName(host);
    connectionFactory.getStandaloneConfiguration().setPort(port);
    return connectionFactory;
  }

  @Bean
  public RedisTemplate<String, UserResponse> redisTemplate() {
    final RedisTemplate<String, UserResponse> redisTemplate = new RedisTemplate<>();
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(UserResponse.class));
    redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
    redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
    redisTemplate.setConnectionFactory(jedisConnectionFactory());
    return redisTemplate;
  }

  @Bean
  public RedisCacheConfiguration redisConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(CACHE_TIME_TO_LIVE));
  }
}

