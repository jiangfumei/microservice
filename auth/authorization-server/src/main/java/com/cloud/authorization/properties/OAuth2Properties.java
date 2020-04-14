package com.cloud.authorization.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuth2Properties {

    private OAuth2ClientProperties[] clients = {};

}