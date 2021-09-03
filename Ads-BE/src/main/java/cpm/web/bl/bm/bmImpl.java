package cpm.web.bl.bm;

import cpm.web.dto.bm.bmDto;
import cpm.web.mapper.request.bm.BmRequest;
import cpm.web.service.execute.ExecuteQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class bmImpl implements bm {
    @Autowired
    ExecuteQueryService executeQueryService;

    @Override
    public List<?> loadBm(BmRequest data) {
        Map<String, String> hmParram = new TreeMap<>();
        hmParram.put("user_id",data.getLoginScope().getId().toString());
        return (List<?>) executeQueryService.executeQuery("view_getbm", hmParram,new bmDto());
    }

    @Override
    public boolean updateBm(BmRequest data) {
        SqlParameterSource sqlParram = new MapSqlParameterSource()
                .addValue("tname", data.getName())
                .addValue("tidbm", data.getIdbm())
                .addValue("tnamebm", data.getNamebm())
                .addValue("ttokenbm", data.getTokenbm())
                .addValue("tprofile_picture_uribm", data.getProfile_picture_uribm())
                .addValue("tverification_statusbm", data.getVerification_statusbm())
                .addValue("tinput", "I")
                .addValue("tuser_id", data.getLoginScope().getId().toString());
        return executeQueryService.executeUpdate("fun_update_acountads", sqlParram);
    }

    @Override
    public boolean deleteBm(BmRequest data) {
        SqlParameterSource sqlParram = new MapSqlParameterSource()
                .addValue("tname", data.getName())
                .addValue("tidbm", data.getIdbm())
                .addValue("tnamebm", data.getNamebm())
                .addValue("ttokenbm", data.getTokenbm())
                .addValue("tprofile_picture_uribm", data.getProfile_picture_uribm())
                .addValue("tverification_statusbm", data.getVerification_statusbm())
                .addValue("tinput", "D")
                .addValue("tuser_id", data.getLoginScope().getId().toString());
        return executeQueryService.executeUpdate("fun_update_acountads", sqlParram);
    }

    @Override
    public boolean updateTokenBm(BmRequest data) {
        SqlParameterSource sqlParram = new MapSqlParameterSource()
                .addValue("tidbm", data.getIdbm())
                .addValue("ttoken_bm_full", data.getToken_bm_full())
                .addValue("tinput", "U")
                .addValue("tuser_id", data.getLoginScope().getId().toString());
        return executeQueryService.executeUpdate("fun_update_token", sqlParram);
    }
}
