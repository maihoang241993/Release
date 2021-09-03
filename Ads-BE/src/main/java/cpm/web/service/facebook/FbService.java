package cpm.web.service.facebook;

import cpm.web.mapper.request.facebook.AccountAds;
import cpm.web.mapper.request.facebook.AccountPartner;
import cpm.web.mapper.request.facebook.BmInfoRequest;

public interface FbService {
    <T> T getBmInfo(BmInfoRequest bmInfoRequest);
    <T> T getListUser(AccountAds accountAds);
    <T> T changePermissionAcount(AccountAds accountAds);
    <T> T deletePermissionAcount(AccountAds accountAds);
    <T> T updateAdminOrEmp(AccountPartner accountPartner);
    <T> T getListAccount(BmInfoRequest bmInfoRequest);
    <T> T getAllListUser(BmInfoRequest bmInfoRequest);
    <T> T updateAuthenAccountPartner(AccountPartner accountPartner);
}
