package cpm.web.dto.telegram;

public class CursorsDto {
    private String before;
    private String after;


    // Getter Methods

    public String getBefore() {
        return before;
    }

    public String getAfter() {
        return after;
    }

    // Setter Methods

    public void setBefore(String before) {
        this.before = before;
    }

    public void setAfter(String after) {
        this.after = after;
    }
}
