package cpm.web.controller.bm;

import cpm.web.bl.bm.bm;
import cpm.web.mapper.request.bm.BmRequest;
import cpm.web.mapper.request.facebook.BmInfoRequest;
import cpm.web.mapper.response.facebook.BmInfoResponse;
import cpm.web.service.facebook.FbService;
import cpm.web.utils.execute.ServicesController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bm")
public class BmController extends ServicesController {

    @Autowired
    bm bm;

    @Autowired
    FbService fbService;

    @PostMapping(value = "/loadBm")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> loadBm(@RequestBody BmRequest data) {
//        String tmp = this.convertData();
        return ResponseEntity.status(HttpStatus.OK).body(bm.loadBm(data));
    }

    @PostMapping(value = "/updateBm")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBm(@RequestBody BmInfoRequest bmInfoRequest) {

        BmInfoResponse bmInfoResponse = fbService.getBmInfo(bmInfoRequest);
        BmRequest bmRequest = new BmRequest();
        bmRequest.setIdbm(bmInfoResponse.getId());
        bmRequest.setName(bmInfoRequest.getName());
        bmRequest.setNamebm(bmInfoResponse.getName());
        bmRequest.setTokenbm(bmInfoRequest.getAccessToken());
        bmRequest.setProfile_picture_uribm(bmInfoResponse.getProfile_picture_uri());
        bmRequest.setVerification_statusbm(bmInfoResponse.getVerification_status());
        bmRequest.setLoginScope(bmInfoRequest.getLoginScope());
        boolean status = false;
        if (bmInfoResponse != null) {
            bm.updateBm(bmRequest);
            status = true;
        }

        return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    @PostMapping(value = "/deleteBm")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBm(@RequestBody BmRequest data) {
//        String tmp = this.convertData();
        return ResponseEntity.status(HttpStatus.OK).body(bm.deleteBm(data));
    }

    @PostMapping(value = "/updateTokenBm")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTokenBm(@RequestBody BmRequest data) {
//        String tmp = this.convertData();
        return ResponseEntity.status(HttpStatus.OK).body(bm.updateTokenBm(data));
    }
}
