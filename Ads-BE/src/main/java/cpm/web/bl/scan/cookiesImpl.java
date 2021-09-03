package cpm.web.bl.scan;

import cpm.web.dto.cookies.CookieDto;
import cpm.web.mapper.request.cookie.CookieRequest;
import cpm.web.mapper.request.scan.ExtensionRequest;
import cpm.web.service.execute.ExecuteQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class cookiesImpl implements cookies {
    @Autowired
    ExecuteQueryService executeQueryService;

    @Override
    public List<?> loadCookies(CookieRequest data) {
        Map<String, String> hmParram = new TreeMap<>();
        hmParram.put("user_id","1=1");
        return (List<?>) executeQueryService.executeQueryAll("view_getcookies", hmParram,new CookieDto());
    }

    @Override
    public boolean updateCookie(ExtensionRequest data) {
        SqlParameterSource sqlParram = new MapSqlParameterSource()
                .addValue("tuser_id", data.getSample1())
                .addValue("tcookie", data.getSample2());
        return executeQueryService.executeUpdate("fun_update_cookies", sqlParram);
    }
}
