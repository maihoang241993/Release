package cpm.web.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BasicParramDto {
    private String tloc_id;
    private LocalDate dtCreated;
    private LocalDate dtUpdated;

    public String getTloc_id() {
        return tloc_id;
    }

    public LocalDate getDtCreated() {
        return dtCreated;
    }

    public LocalDate getDtUpdated() {
        return dtUpdated;
    }

    public void setTloc_id(String tloc_id) {
        this.tloc_id = tloc_id;
    }

    public void setDtCreated(LocalDate dtCreated) {
        this.dtCreated = dtCreated;
    }

    public void setDtUpdated(LocalDate dtUpdated) {
        this.dtUpdated = dtUpdated;
    }
}
