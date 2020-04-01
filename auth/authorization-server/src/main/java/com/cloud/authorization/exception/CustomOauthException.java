package com.cloud.authorization.exception;

import com.cloud.common.base.vo.Result;
import com.cloud.common.util.ResultUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/*
@EqualsAndHashCode(callSuper = true)
@Data
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
class CustomOauthException extends OAuth2Exception {

    private final Result result;

    CustomOauthException(OAuth2Exception oAuth2Exception) {
        super(oAuth2Exception.getSummary(), oAuth2Exception);
        */
/*this.result = ResultUtil.error(AuthErrorType.valueOf(oAuth2Exception.getOAuth2ErrorCode().toUpperCase()), oAuth2Exception.getMessage());*//*

        this.result = ResultUtil.error(Integer.valueOf(oAuth2Exception.getOAuth2ErrorCode()), oAuth2Exception.getMessage());
    }
}*/
