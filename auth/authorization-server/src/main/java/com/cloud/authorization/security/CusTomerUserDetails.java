package com.cloud.authorization.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CusTomerUserDetails implements UserDetails {

    static final long serialVersionUID = -7588980448693010399L;
    private String username;

    private String password;

    private boolean enabled = true;

    private String userId;

    private String clientId;

    private Collection<? extends GrantedAuthority> authorities;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public static class CustomUserDetailsBuilder {
        private CusTomerUserDetails userDetails = new CusTomerUserDetails();

        public CustomUserDetailsBuilder withUsername(String username) {
            userDetails.setUsername(username);
            userDetails.setAuthorities(null);
            return this;
        }

        public CustomUserDetailsBuilder withPassword(String password) {
            userDetails.setPassword(password);
            return this;
        }

        public CustomUserDetailsBuilder withClientId(String clientId) {
            userDetails.setClientId(clientId);
            return this;
        }

        public CustomUserDetailsBuilder withUserId(String userId) {
            userDetails.setUserId(userId);
            return this;
        }

        public CusTomerUserDetails build() {
            return userDetails;
        }
    }
}
