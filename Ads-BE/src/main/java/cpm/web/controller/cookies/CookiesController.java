package cpm.web.controller.cookies;

import cpm.web.bl.scan.cookies;
import cpm.web.mapper.request.cookie.CookieRequest;
import cpm.web.utils.execute.ServicesController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cookies")
public class CookiesController extends ServicesController {

    @Autowired
    cookies cookies;

    @PostMapping(value = "/loadCookies")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> loadCookies(@RequestBody CookieRequest data) {
//        String tmp = this.convertData();
        return ResponseEntity.status(HttpStatus.OK).body(cookies.loadCookies(data));
    }
}
