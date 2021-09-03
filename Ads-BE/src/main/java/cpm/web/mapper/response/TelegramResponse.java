package cpm.web.mapper.response;

import cpm.web.dto.fb.ResultDto;

public class TelegramResponse {
    private boolean ok;
    ResultDto ResultObject;


    // Getter Methods

    public boolean getOk() {
        return ok;
    }

    public ResultDto getResult() {
        return ResultObject;
    }

    // Setter Methods

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setResult(ResultDto resultObject) {
        this.ResultObject = resultObject;
    }
}
