package cpm.web.service.facebook.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfb.*;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.json.JsonObject;
import com.restfb.types.send.SendResponse;
import cpm.web.mapper.request.facebook.AccountPartner;
import cpm.web.service.facebook.common.modal.ParamRequest;
import cpm.web.service.task.ScheduleTasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class FacebookCommon {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static String accessToken = null;
    private static final String FIELDS = "fields";

    /**
     * Set token
     *
     * @param accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Get data dynamic
     * Facebook
     *
     * @param <T>
     * @return
     */
    public <T> T get(ParamRequest paramRequest) {
        log.info("Start get : ", log.getClass());
        FacebookClient facebookClient = new DefaultFacebookClient(this.accessToken, Version.LATEST);
        List<JsonObject> jsonObject = new ArrayList<>();
        requireNonNull(paramRequest, "paramRequest not null");
        try {
            paramRequest.getObject().forEach(item -> {
                jsonObject.add(
                        facebookClient.fetchObject(
                                item,
                                JsonObject.class, Parameter.with(FIELDS, paramRequest.getFields())
                        )
                );
            });
            log.info("Data facebook response: ", jsonObject);

        } catch (FacebookOAuthException exception) {
            log.error("Error data facebook response: ", exception);
            throw new RuntimeException(exception);
        }
        return (T) jsonObject;
    }


    public <T> T post(AccountPartner accountPartner) {
        log.info("Start get : ", log.getClass());
        FacebookClient facebookClient = new DefaultFacebookClient(this.accessToken, Version.LATEST);
        JsonObject jsonObject = null;
        requireNonNull(accountPartner, "accountPartner not null");
        List<Parameter> parameterList = new ArrayList<>();
        for(Map.Entry<String, String> entry1 : accountPartner.getParameter().entrySet()) {
            parameterList.add(Parameter.with(entry1.getKey(), entry1.getValue()));
        }

        try {
            jsonObject = facebookClient.publish(
                    accountPartner.getConnection(),
                    JsonObject.class,
                    parameterList.toArray(new Parameter[0])
            );
            log.info("Data facebook response: ", jsonObject);

        } catch (FacebookOAuthException exception) {
            log.error("Error data facebook response: ", exception);
            throw new RuntimeException(exception);
        }
        return (T) jsonObject;
    }
}
