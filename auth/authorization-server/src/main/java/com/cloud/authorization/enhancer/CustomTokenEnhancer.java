package com.cloud.authorization.enhancer;

import com.cloud.authorization.entity.User;
import com.cloud.authorization.entity.UserEntity;
import com.google.common.collect.Maps;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Map;

/**
 * 自定义jwt
 */
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        //Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) oAuth2Authentication.getPrincipal();
        //User oo = (User) oAuth2Authentication.getUserAuthentication().getPrincipal();
        String userName =user.getUsername();
        final Map<String, Object> additionalInfo = Maps.newHashMap();
        additionalInfo.put("username",userName);
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}
