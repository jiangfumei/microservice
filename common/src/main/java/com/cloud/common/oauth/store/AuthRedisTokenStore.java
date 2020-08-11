package com.cloud.common.oauth.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.token.TokenStore;

public class AuthRedisTokenStore {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Bean
    public TokenStore tokenStore(){
        RedisTempleteTokenStore redisTempleteTokenStore = new RedisTempleteTokenStore();
        redisTempleteTokenStore.setRedisTemplate(redisTemplate);
        return redisTempleteTokenStore;
    }
}
