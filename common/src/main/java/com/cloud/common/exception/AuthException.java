package com.cloud.common.exception;

import com.cloud.common.base.BasicController;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

public class AuthException {

    public static class NoLogin extends ServiceException {
        public NoLogin(String message, AuthenticationException ex) {
            super(message, ex);
        }

        @Override
        public String i18n(MessageSource messageSource) {
            return super.i18n(messageSource, "no.login.error", this.getData());
        }

        @Override
        public int httpStatus() {
            return BasicController.local.get() == null ? 401 : 403;
        }
    }

    public static class NoRight extends ServiceException {
        public NoRight(String message, AccessDeniedException ex) {
            super(message, ex);
        }

        @Override
        public String i18n(MessageSource messageSource) {
            return super.i18n(messageSource, "no.right.error", this.getData());
        }

        @Override
        public int httpStatus() {
            return 403;
        }
    }
}
