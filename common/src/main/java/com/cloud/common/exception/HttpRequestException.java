package com.cloud.common.exception;

import org.springframework.context.MessageSource;

public class HttpRequestException extends I18nException {

    private static final long serialVersionUID = 1L;
    private String key;

    public HttpRequestException(String message) {
        super(message);
    }

    public static HttpRequestException newI18N(String i18n, Object... args) {
        return new HttpRequestException("I18N code : " + i18n, i18n, args);
    }

    protected HttpRequestException(String message, String i18n, Object... args) {
        super(message);
        this.key = i18n;
        this.setData(args);
    }

    public HttpRequestException(Exception ex, String message) {
        super(message, ex);
    }

    @Override
    public String i18n(MessageSource messageSource) {
        if (this.key == null) {
            return this.getMessage();
        }
        return this.i18n(messageSource, this.key, this.getData());
    }


}
