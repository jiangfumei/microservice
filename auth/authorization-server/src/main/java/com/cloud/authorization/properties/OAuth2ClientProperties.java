package com.cloud.authorization.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuth2ClientProperties {

    private String clientId;

    private String clientSecret;

    // private String resourceIds;

    private int accessTokenValidatySeconds;

    private int refreshTokenValiditySeconds;

}
