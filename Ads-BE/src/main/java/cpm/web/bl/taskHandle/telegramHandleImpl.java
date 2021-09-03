package cpm.web.bl.taskHandle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cpm.web.dto.bm.bmDto;
import cpm.web.dto.telegram.DataDto;
import cpm.web.service.execute.ExecuteQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class telegramHandleImpl implements telegramHandle {

    @Autowired
    ExecuteQueryService executeQueryService;

    @Override
    public List<?> getDataBm() {
        Map<String, String> hmParram = new TreeMap<>();
        hmParram.put("user_id","1=1");
        return (List<?>) executeQueryService.executeQueryAll("view_getbm", hmParram,new bmDto());
    }

    @Override
    public boolean addDataShareMail(String idBm,DataDto data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SqlParameterSource sqlParram = new MapSqlParameterSource()
                .addValue("tid_bm", idBm)
                .addValue("tid_mail", data.getId())
                .addValue("tdata_json", objectMapper.writeValueAsString(data));
        return executeQueryService.executeUpdate("fun_update_share_mails", sqlParram);
    }
}
