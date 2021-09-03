package cpm.web.mapper.request.facebook;

import lombok.Data;

@Data
public class AccountAds {
    private String idAccount;
    private String accessToken;
    private String idBm;
    private String task;
    private String user;
    private String role;
}
