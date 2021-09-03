package cpm.web.mapper.request.facebook;

import cpm.web.mapper.response.LoginResponse;
import lombok.Data;

@Data
public class BmInfoRequest {
    private LoginResponse loginScope;
    private String idBm;
    private String name;
    private String accessToken;
    private String accessTokenFull;
}
