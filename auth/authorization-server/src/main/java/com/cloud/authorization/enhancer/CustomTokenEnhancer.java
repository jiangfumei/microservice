package com.cloud.authorization.enhancer;

import com.cloud.authorization.entity.User;
import com.google.common.collect.Maps;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Map;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/**
 * 自定义jwt
 */
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        final Map<String, Object> additionalInfo = Maps.newHashMap();
        /*User user = (User) oAuth2Authentication.getPrincipal();*/
        //User user = (User) oAuth2Authentication.getUserAuthentication().getPrincipal();
        //自定义token内容，加入组织机构信息
        additionalInfo.put("jwt-extra", oAuth2Authentication.getName()+randomAlphabetic(4));
        //additionalInfo.put("username",user.getUsername());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}
