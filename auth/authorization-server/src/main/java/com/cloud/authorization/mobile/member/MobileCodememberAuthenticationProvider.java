package com.cloud.authorization.mobile.member;

import com.cloud.authorization.openid.member.OpenIdMemberAuthenticationToken;
import com.cloud.authorization.service.UserSecurityService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author mall
 */
public class MobileCodememberAuthenticationProvider implements AuthenticationProvider {

    private UserSecurityService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        OpenIdMemberAuthenticationToken authenticationToken = (OpenIdMemberAuthenticationToken) authentication;
        String openId = (String) authenticationToken.getPrincipal();
        UserDetails user = userDetailsService.loadMemberByMobile(openId);
        if (user == null) {
            throw new InternalAuthenticationServiceException("mobile错误");
        }
        MobileCodeMemberAuthenticationToken authenticationResult = new MobileCodeMemberAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OpenIdMemberAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserSecurityService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserSecurityService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
