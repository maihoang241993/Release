package cpm.web.service.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.json.JsonObject;
import cpm.web.bl.taskHandle.telegramHandle;
import cpm.web.dto.bm.bmDto;
import cpm.web.dto.telegram.DataDto;
import cpm.web.dto.telegram.SettingTelegramDto;
import cpm.web.mapper.response.facebook.FbResponse;
import cpm.web.utils.telegram.BotSendMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduleTasks {

    @Autowired
    telegramHandle telegramHandle;

    private static final Logger log = LoggerFactory.getLogger(ScheduleTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");

    BotSendMessages botSendMessages = new BotSendMessages();

    @Scheduled(fixedRate = 300000)
//    @Scheduled(fixedRate = 5000)
    public void sendDataToTelegram() throws JsonProcessingException {
        List<?> listDataBm = telegramHandle.getDataBm();
        if (listDataBm.size() > 0) {
            for (Object obj : listDataBm) {
                bmDto bmRequest = (bmDto) obj;
                FbResponse fbResponse = getResponseFB(bmRequest.getIdbm(), bmRequest.getTokenbm());
                if (fbResponse != null) {
                    for (DataDto data : fbResponse.getData()) {
                        if (data.getStatus().equals("PENDING")
                                && telegramHandle.addDataShareMail(bmRequest.getIdbm(), data)) {
                            String outputMsg = String.format("Thời gian: %s\n" +
                                    "Email: %s\n" +
                                    "Link: %s\n" +
                                    "Role: %s", dateFormat.format(new Date()),
                                    data.getEmail(),
                                    data.getInvite_link(),
                                    data.getRole()
                            );
                            SettingTelegramDto settingTelegramDto = new SettingTelegramDto();
                            settingTelegramDto.setToken("1731184123:AAFnkeMPX0XTM7Jwa1GHPLPYMh4bm6m_3j0");
                            settingTelegramDto.setId("-514641946");
                            settingTelegramDto.setMsg(outputMsg);
                            botSendMessages.sendRequestMsg(settingTelegramDto);
                        }
                    }
                }
            }
        }
    }

    private FbResponse getResponseFB(String userID, String accessToken) throws JsonProcessingException {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);
        try {
            JsonObject userData = facebookClient.fetchObject(String.format("%s/pending_users", userID),
                    JsonObject.class, Parameter.with("fields", "id, role, email, invite_link, status"));
            ObjectMapper objectMapper = new ObjectMapper();

            FbResponse fbResponse = objectMapper.readValue(userData.toString(), FbResponse.class);

            if (fbResponse != null && fbResponse.getData().size() > 0) {
                log.info("Success {}", fbResponse.getData());
            } else {
                log.error("Error #getResponseFB");
            }
            return fbResponse;

        } catch (FacebookOAuthException facebookOAuthException) {
            log.error("Token Error: {}", facebookOAuthException.getErrorMessage());
            return null;
        }
    }
}
