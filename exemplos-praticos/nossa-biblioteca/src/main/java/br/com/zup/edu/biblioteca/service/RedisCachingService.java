package br.com.zup.edu.biblioteca.service;

import br.com.zup.edu.biblioteca.ResponseEntitySerializer;
import br.com.zup.edu.biblioteca.controller.payment.CachedResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCachingService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ResponseEntitySerializer serializer;

    public RedisCachingService(RedisTemplate<String, String> redisTemplate, ResponseEntitySerializer serializer) {
        this.redisTemplate = redisTemplate;
        this.serializer = serializer;
    }

    public void set(String key, CachedResponse responseEntity) throws JsonProcessingException {
        String json = serializer.serialize(responseEntity);
        redisTemplate.opsForValue().set(key, json);
        redisTemplate.expire(key, 60, TimeUnit.SECONDS);
    }

    public CachedResponse get(String key) throws JsonProcessingException {
        String json = redisTemplate.opsForValue().get(key);
        if (json != null) {
            return serializer.deserialize(json);
        }
        return null;
    }
}

