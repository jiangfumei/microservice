package com.cloud.authorization.converter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

    @Override
    public OAuth2Authentication extractAuthentication(Map<String,?> claims){
        OAuth2Authentication auth2Authentication =super.extractAuthentication(claims);
        auth2Authentication.setDetails(claims);
        return auth2Authentication;
    }

    /*@Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        LinkedHashMap response = new LinkedHashMap();
        response.put("user_name", authentication.getName());
        response.put("name", ((User) authentication.getPrincipal()).getName());
        response.put("id", ((User) authentication.getPrincipal()).getId());
        response.put("createAt", ((User) authentication.getPrincipal()).getCreateAt());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }

        return response;
    }*/
}
