package com.cloud.authorization.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Configuration
public class AuthenticationService {

    @Resource
    ValueOperations<String, Serializable> redis;

    public static enum AuthorityType {
        Register, Admin, Vistor;
        private final GrantedAuthority authority;

        AuthorityType() {
            this.authority = new SimpleGrantedAuthority("ROLE_" + this.name());
        }

        public GrantedAuthority getAuthority() {
            return authority;
        }

        public static GrantedAuthority[] getAllAuthority() {
            return new GrantedAuthority[]{Register.getAuthority(), Admin.getAuthority(),Vistor.authority};
        }

    }

    public static final String REDIS_PREFIX = "access-token:";

    public void createToken(String token, ApiToken auth) {
        String sha2 = DigestUtils.sha1Hex(token.getBytes());
        redis.set(REDIS_PREFIX + sha2, auth, 10, TimeUnit.DAYS);
    }

    public Optional<ApiToken> get(String token) {
        String sha2 = DigestUtils.sha1Hex(token.getBytes());
        ApiToken api = (ApiToken) redis.get(REDIS_PREFIX + sha2);
        return Optional.ofNullable(api);
    }

    public void delete(String token) {
        String sha2 = DigestUtils.sha1Hex(token.getBytes());
        redis.set(REDIS_PREFIX + sha2, null);
    }

    public static class ApiToken extends AbstractAuthenticationToken {
        private final String credentials;
        private final String principal;

        public ApiToken(String uuid, String password, GrantedAuthority... auth) {
            super(Arrays.asList(auth));
            this.principal = uuid;
            this.credentials = password;
        }

        @Override
        public Object getCredentials() {
            return credentials;
        }

        @Override
        public Object getPrincipal() {
            return principal;
        }
    }


}
