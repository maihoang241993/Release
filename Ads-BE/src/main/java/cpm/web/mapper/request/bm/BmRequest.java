package cpm.web.mapper.request.bm;

import cpm.web.mapper.response.LoginResponse;

public class BmRequest {
    private LoginResponse loginScope;
    private String name;
    private String idbm;
    private String namebm;
    private String tokenbm;
    private String token_bm_full;
    private String profile_picture_uribm;
    private String verification_statusbm;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdbm() {
        return idbm;
    }

    public void setIdbm(String idbm) {
        this.idbm = idbm;
    }

    public String getNamebm() {
        return namebm;
    }

    public void setNamebm(String namebm) {
        this.namebm = namebm;
    }

    public String getTokenbm() {
        return tokenbm;
    }

    public void setTokenbm(String tokenbm) {
        this.tokenbm = tokenbm;
    }

    public String getProfile_picture_uribm() {
        return profile_picture_uribm;
    }

    public void setProfile_picture_uribm(String profile_picture_uribm) {
        this.profile_picture_uribm = profile_picture_uribm;
    }

    public String getVerification_statusbm() {
        return verification_statusbm;
    }

    public void setVerification_statusbm(String verification_statusbm) {
        this.verification_statusbm = verification_statusbm;
    }

    public LoginResponse getLoginScope() {
        return loginScope;
    }

    public void setLoginScope(LoginResponse loginScope) {
        this.loginScope = loginScope;
    }

    public String getToken_bm_full() {
        return token_bm_full;
    }

    public void setToken_bm_full(String token_bm_full) {
        this.token_bm_full = token_bm_full;
    }
}
