package cpm.web.bl.settings;

import cpm.web.mapper.request.settings.SettingRequest;

import java.util.List;

public interface setting {
    List<?> loadSetting(SettingRequest data);

    boolean updateSetting(SettingRequest data);
}
