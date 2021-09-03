package cpm.web.service.facebook.common.modal;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ParamRequest {
    private ArrayList<String> object = new ArrayList<>();
    private String fields;
}
