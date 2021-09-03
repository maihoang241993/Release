package cpm.web.bl.settings;

import cpm.web.dto.bm.bmDto;
import cpm.web.dto.settings.settingsDto;
import cpm.web.mapper.request.bm.BmRequest;
import cpm.web.mapper.request.settings.SettingRequest;
import cpm.web.service.execute.ExecuteQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class settingImpl implements setting {
    @Autowired
    ExecuteQueryService executeQueryService;

    @Override
    public List<?> loadSetting(SettingRequest data) {
        Map<String, String> hmParram = new TreeMap<>();
        hmParram.put("user_id", data.getLoginScope().getId().toString());
        return (List<?>) executeQueryService.executeQuery("view_setting", hmParram, new settingsDto());
    }

    @Override
    public boolean updateSetting(SettingRequest data) {
        SqlParameterSource sqlParram = new MapSqlParameterSource()
                .addValue("ttoken", data.getToken())
                .addValue("tinput", "U")
                .addValue("tuser_id", data.getLoginScope().getId().toString());
        return executeQueryService.executeUpdate("fun_update_settings", sqlParram);
    }
}
