package cpm.web.utils.session;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Component
@SessionScope
@Data
public class SessionContext implements Serializable {
    private Long id;
    private String sessionId;
}
