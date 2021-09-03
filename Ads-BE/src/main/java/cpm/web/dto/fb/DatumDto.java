package cpm.web.dto.fb;

import lombok.Data;

@Data
public class DatumDto {
    public String account_id;
    public int disable_reason;
    public String name;
    public String currency;
    public int account_status;
    public BusinessDto business;
    public String id;
    public funding_source_details funding_source_details;

    @Data
    class funding_source_details {
        public String id;
        public String display_string;
        public String type;
    }
}
