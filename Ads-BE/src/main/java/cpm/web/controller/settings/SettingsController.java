package cpm.web.controller.settings;

import cpm.web.bl.settings.setting;
import cpm.web.mapper.request.settings.SettingRequest;
import cpm.web.service.facebook.FbService;
import cpm.web.utils.execute.ServicesController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/setting")
public class SettingsController extends ServicesController {

    @Autowired
    setting setting;

    @Autowired
    FbService fbService;

    @PostMapping(value = "/loadSetting")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> loadSetting(@RequestBody SettingRequest data) {
//        String tmp = this.convertData();
        return ResponseEntity.status(HttpStatus.OK).body(setting.loadSetting(data));
    }

    @PostMapping(value = "/updateSetting")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateSetting(@RequestBody SettingRequest data) {
//        String tmp = this.convertData();
        return ResponseEntity.status(HttpStatus.OK).body(setting.updateSetting(data));
    }
}
