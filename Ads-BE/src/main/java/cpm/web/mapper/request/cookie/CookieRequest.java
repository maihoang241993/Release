package cpm.web.mapper.request.cookie;

import cpm.web.mapper.response.LoginResponse;

public class CookieRequest {
    private LoginResponse loginScope;

    public LoginResponse getLoginScope() {
        return loginScope;
    }

    public void setLoginScope(LoginResponse loginScope) {
        this.loginScope = loginScope;
    }
}
