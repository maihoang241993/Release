package cpm.web.dto.telegram;

public class PagingDto {
    CursorsDto CursorsObject;


    // Getter Methods

    public CursorsDto getCursors() {
        return CursorsObject;
    }

    // Setter Methods

    public void setCursors(CursorsDto cursorsObject) {
        this.CursorsObject = cursorsObject;
    }
}
