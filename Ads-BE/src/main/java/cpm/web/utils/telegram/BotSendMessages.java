package cpm.web.utils.telegram;

import cpm.web.dto.telegram.SettingTelegramDto;
import cpm.web.mapper.response.TelegramResponse;
import cpm.web.service.task.ScheduleTasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

public class BotSendMessages {

    private static final Logger log = LoggerFactory.getLogger(ScheduleTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");

    public void sendRequestMsg(SettingTelegramDto configDto) {
        String URL = String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",
                configDto.getToken(), configDto.getId(), configDto.getMsg());

        RestTemplate restTemplate = new RestTemplate();

        // Gửi yêu cầu với phương thức POST.
        TelegramResponse e = restTemplate.postForObject(URL, null, TelegramResponse.class);

        if (e != null && e.getOk()) {
            log.info("Success {}", e.getResult().getMessage_id());
        } else {
            log.error("Error #sendRequestMsg!");
        }
    }
}
