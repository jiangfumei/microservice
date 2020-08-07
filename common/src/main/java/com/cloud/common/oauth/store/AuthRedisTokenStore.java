package com.cloud.common.oauth.store;

import com.cloud.common.oauth.config.TokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

public class AuthRedisTokenStore {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

   /* @Bean
    public TokenStore tokenStore(){
        //RedisT
    }*/
}
