package org.aykhan.springdataprovidermongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;


@Configuration
public class RedisConfig {
    @Bean
    protected JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public RedisCacheConfiguration redisCacheManager() {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(60 * 5))
                .disableCachingNullValues();
    }
}

