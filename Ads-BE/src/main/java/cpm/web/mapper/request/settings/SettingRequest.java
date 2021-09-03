package cpm.web.mapper.request.settings;

import cpm.web.mapper.response.LoginResponse;
import lombok.Data;

@Data
public class SettingRequest {
    private LoginResponse loginScope;
    private int token;
}
