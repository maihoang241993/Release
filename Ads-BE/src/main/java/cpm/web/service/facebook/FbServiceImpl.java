package cpm.web.service.facebook;

import com.facebook.ads.sdk.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfb.json.JsonObject;
import cpm.web.mapper.request.facebook.AccountAds;
import cpm.web.mapper.request.facebook.AccountPartner;
import cpm.web.mapper.request.facebook.BmInfoRequest;
import cpm.web.mapper.response.facebook.BmInfoResponse;
import cpm.web.mapper.response.facebook.ListAccountResponse;
import cpm.web.mapper.response.facebook.StatusResponse;
import cpm.web.service.facebook.common.FacebookCommon;
import cpm.web.service.facebook.common.modal.ParamRequest;
import cpm.web.utils.common.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FbServiceImpl extends FacebookCommon implements FbService {

    private static final Logger log = LoggerFactory.getLogger(FbServiceImpl.class);

    @Override
    public <T> T getBmInfo(BmInfoRequest bmInfoRequest) {
        try {
            log.info("Start  ", bmInfoRequest);
            this.setAccessToken(bmInfoRequest.getAccessToken());
            ParamRequest paramRequest = new ParamRequest();
            paramRequest.setObject(new ArrayList<String>() {{
                add(bmInfoRequest.getIdBm());
            }});
            paramRequest.setFields("id,name,verification_status,profile_picture_uri,primary_page");
            T typeObj = (T) ConvertUtils.convertJsonToObject((List<JsonObject>) this.get(paramRequest), new BmInfoResponse().getClass());
            log.info("End ", typeObj);
            return typeObj;
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("Error getBmInfo: ", e);
        }
    }

    @Override
    public <T> T getListUser(AccountAds accountAds) {
        try {
            log.info("Start  ", accountAds);
            APIContext context = new APIContext(accountAds.getAccessToken());
            AdAccount adAccount = new AdAccount(accountAds.getIdAccount(), context);
            Map<String, Object> stringObjectMap = new HashMap<>();
            stringObjectMap.put("business", accountAds.getIdBm());
            APINodeList<AssignedUser> list = adAccount.getAssignedUsers().requestAllFields().execute(stringObjectMap);
            return (T) list;
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("Error getListUser: ", e);
        }
    }

    @Override
    public <T> T changePermissionAcount(AccountAds accountAds) {
        try {
            log.info("Start  ", accountAds);
            APIContext context = new APIContext(accountAds.getAccessToken());
            AdAccount adAccount = new AdAccount(accountAds.getIdAccount(), context);
            Map<String, Object> stringObjectMap = new HashMap<>();
            stringObjectMap.put("business", accountAds.getIdBm());

            Map<String, Object> param = new HashMap<>();
            param.put("user", accountAds.getUser());
            param.put("tasks", accountAds.getTask());

            AdAccount list = adAccount.createAssignedUser().setParams(param).execute(stringObjectMap);
            return (T) list;
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("Error changePermissionAcount: ", e);
        }
    }

    @Override
    public <T> T deletePermissionAcount(AccountAds accountAds) {
        try {
            log.info("Start  ", accountAds);
            APIContext context = new APIContext(accountAds.getAccessToken());
            AdAccount adAccount = new AdAccount(accountAds.getIdAccount(), context);
            Map<String, Object> stringObjectMap = new HashMap<>();
            stringObjectMap.put("business", accountAds.getIdBm());

            Map<String, Object> param = new HashMap<>();
            param.put("user", accountAds.getUser());
            param.put("tasks", accountAds.getTask());

            APINodeList<APINode> list = adAccount.deleteAssignedUsers().setParams(param).execute(stringObjectMap);
            return (T) list;
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("Error deletePermissionAcount: ", e);
        }
    }

    @Override
    public <T> T updateAdminOrEmp(AccountPartner accountPartner) {
        try {
            try {
                log.info("Start  ", accountPartner);
                this.setAccessToken(accountPartner.getAccessToken());

                T typeObj = (T) this.post(accountPartner);
                log.info("End ", typeObj);

                return typeObj;
            } catch (Exception e) {
                log.error(e.toString());
                throw new RuntimeException("Error updateAdminOrEmp: ", e);
            }
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("Error updateAdminOrEmp: ", e);
        }
    }

    @Override
    public <T> T getListAccount(BmInfoRequest bmInfoRequest) {
        try {
            log.info("Start ", bmInfoRequest);


            APIContext context = new APIContext(bmInfoRequest.getAccessToken());
            Business business = new Business(bmInfoRequest.getIdBm(), context);
            String field = "[\"account_id\",\"disable_reason\",\"name\",\"currency\",\"account_status\",\"business\",\"funding_source_details\"]";
            Map<String, Object> stringObjectMap = new HashMap<>();
            stringObjectMap.put("limit", "1000");
            APINodeList<AdAccount> listOwenAdAccount = business.getOwnedAdAccounts().requestField(field).execute(stringObjectMap);
            APINodeList<AdAccount> listClientAdAccounts = business.getClientAdAccounts().requestField(field).execute(stringObjectMap);

            ObjectMapper jsonMP1 = new ObjectMapper();
            Object objectMapper = jsonMP1.readValue(listOwenAdAccount.getRawResponse(), Object.class);
            ObjectMapper jsonMP2 = new ObjectMapper();
            Object objectMapper1 = jsonMP2.readValue(listClientAdAccounts.getRawResponse(), Object.class);

            List<Object> objectList = new ArrayList<>();
            objectList.add(objectMapper);
            objectList.add(objectMapper1);

            return (T) objectList;
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("Error getListAccount: ", e);
        }
    }

    @Override
    public <T> T getAllListUser(BmInfoRequest bmInfoRequest) {
        try {
            log.info("Start ", bmInfoRequest);
            this.setAccessToken(bmInfoRequest.getAccessToken());
            ParamRequest paramRequest = new ParamRequest();
            paramRequest.setObject(new ArrayList<String>() {
                {
                    add(bmInfoRequest.getIdBm() + "/business_users");
                }
            });
            paramRequest.setFields("[\"account_id\",\"disable_reason\",\"name\",\"currency\",\"account_status\",\"business\",\"funding_source_details\"]");
            T typeObj = (T) ConvertUtils.convertJsonToObject(
                    (List<JsonObject>) this.get(paramRequest),
                    new ListAccountResponse().getClass()
            );
            log.info("End ", typeObj);

            return typeObj;
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("Error getListAccount: ", e);
        }
    }

    @Override
    public <T> T updateAuthenAccountPartner(AccountPartner accountPartner) {
        try {
            log.info("Start ", accountPartner);
            this.setAccessToken(accountPartner.getAccessToken());

            T typeObj = (T) ConvertUtils.convertJsonToObject((JsonObject) this.post(accountPartner),
                    new StatusResponse().getClass());

            log.info("End ", typeObj);

            return (T) typeObj;
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("Error getListAccount: ", e);
        }
    }

}
