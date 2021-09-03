package cpm.web.bl.taskHandle;

import com.fasterxml.jackson.core.JsonProcessingException;
import cpm.web.dto.telegram.DataDto;

import java.util.List;

public interface telegramHandle {
    List<?> getDataBm();
    boolean addDataShareMail(String idBm, DataDto data) throws JsonProcessingException;
}
