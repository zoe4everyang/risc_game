package edu.duke.ece651.risk_game.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MyRedisUtilTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    public void setUp() {
        // 清空Redis
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    @Test
    public void testRedisTemplate() {
        String key = "test-key";
        String value = "test-value";
        assertFalse(redisTemplate.hasKey(key));

        redisTemplate.opsForValue().set(key, value);
        assertTrue(redisTemplate.hasKey(key));
        assertEquals(value, redisTemplate.opsForValue().get(key));
    }
}