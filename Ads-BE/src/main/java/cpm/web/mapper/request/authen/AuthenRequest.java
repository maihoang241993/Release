package cpm.web.mapper.request.authen;

import javax.validation.constraints.NotBlank;

public class AuthenRequest {
    @NotBlank
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
