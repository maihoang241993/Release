package cpm.web.mapper.request.facebook;

import cpm.web.mapper.response.LoginResponse;
import lombok.Data;

import java.util.TreeMap;

@Data
public class AccountPartner {
    private LoginResponse loginScope;
    private String business;
    private String accessToken;
    private String connection;
    private TreeMap<String, String> parameter;
}
