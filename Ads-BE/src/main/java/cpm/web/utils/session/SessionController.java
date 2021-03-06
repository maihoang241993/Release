package cpm.web.utils.session;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.Instant;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    @Autowired
    private SessionContext sessionContext;

    @GetMapping("/session")
    public String getSession(HttpSession session) {
        return session.getId();
    }

    @GetMapping("/identify")
    public IdentificationBean getIdentification(HttpSession session) {
        if (sessionContext.getId() == null) {
            sessionContext.setId(Instant.now().toEpochMilli());
            sessionContext.setSessionId(session.getId());
        }
        IdentificationBean bean = new IdentificationBean();
        BeanUtils.copyProperties(sessionContext, bean);

        return bean;
    }
}
