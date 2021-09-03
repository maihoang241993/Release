package cpm.web.utils.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IdentificationBean implements Serializable {
    private Long id;
    private String sessionId;
}
