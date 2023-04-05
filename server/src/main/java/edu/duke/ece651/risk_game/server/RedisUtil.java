package edu.duke.ece651.risk_game.server;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
    private static RedisTemplate<String, Object> redisTemplate;

    public static RedisTemplate<String, Object> getRedisTemplate() {
        if (redisTemplate == null) {
            RedisConnectionFactory factory = new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
            redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(factory);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
            redisTemplate.afterPropertiesSet();
        }
        return redisTemplate;
    }
}

