package cpm.web.bl.scan;

import cpm.web.mapper.request.cookie.CookieRequest;
import cpm.web.mapper.request.scan.ExtensionRequest;

import java.util.List;

public interface cookies {
    List<?> loadCookies(CookieRequest data);
    boolean updateCookie(ExtensionRequest data);
}
