package cpm.web.mapper.response.facebook;

import cpm.web.dto.fb.PrimaryPageDto;
import lombok.Data;

@Data
public class BmInfoResponse {
    private String id;
    private String name;
    private String profile_picture_uri;
    private String verification_status;
    private PrimaryPageDto primary_page;
}