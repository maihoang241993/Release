package cpm.web.controller.fb;

import com.facebook.ads.sdk.APINode;
import com.facebook.ads.sdk.APINodeList;
import com.facebook.ads.sdk.AdAccount;
import com.facebook.ads.sdk.AssignedUser;
import cpm.web.mapper.request.facebook.AccountAds;
import cpm.web.mapper.request.facebook.AccountPartner;
import cpm.web.mapper.request.facebook.BmInfoRequest;
import cpm.web.service.facebook.FbService;
import cpm.web.utils.execute.ServicesController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/fb")
public class FbController extends ServicesController {

    @Autowired
    FbService fbService;

    @PostMapping(value = "/getListAccount")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getListAccount(@RequestBody BmInfoRequest bmInfoRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(fbService.getListAccount(bmInfoRequest));
    }

    @PostMapping(value = "/getAllListUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllListUser(@RequestBody BmInfoRequest bmInfoRequest) {

        return ResponseEntity.status(HttpStatus.OK).body(fbService.getAllListUser(bmInfoRequest));
    }

    @PostMapping(value = "/updateAuthenAccountPartner")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAuthenAccountPartner(@RequestBody AccountPartner accountPartner) {

        return ResponseEntity.status(HttpStatus.OK).body(fbService.updateAuthenAccountPartner(accountPartner));
    }

    @PostMapping(value = "/getListUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getListUser(@RequestBody AccountAds accountAds) {
        APINodeList<AssignedUser> list = fbService.getListUser(accountAds);
        return ResponseEntity.status(HttpStatus.OK).body(list.getRawResponse());
    }

    @PostMapping(value = "/changePermissionAcount")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changePermissionAcount(@RequestBody AccountAds accountAds) {
        AdAccount adAccount = fbService.changePermissionAcount(accountAds);
        return ResponseEntity.status(HttpStatus.OK).body(adAccount.getRawValue());
    }

    @PostMapping(value = "/deletePermissionAcount")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePermissionAcount(@RequestBody AccountAds accountAds) {
        APINodeList<APINode> list = fbService.deletePermissionAcount(accountAds);
        return ResponseEntity.status(HttpStatus.OK).body(list.getRawResponse());
    }

    @PostMapping(value = "/updateAdminOrEmp")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAdminOrEmp(@RequestBody AccountPartner accountPartner) {
        return ResponseEntity.status(HttpStatus.OK).body(fbService.updateAdminOrEmp(accountPartner));
    }
}
