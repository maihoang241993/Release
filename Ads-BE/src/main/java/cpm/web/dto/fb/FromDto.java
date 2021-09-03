package cpm.web.dto.fb;

public class FromDto {
    private float id;
    private boolean is_bot;
    private String first_name;
    private String username;


    // Getter Methods

    public float getId() {
        return id;
    }

    public boolean getIs_bot() {
        return is_bot;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getUsername() {
        return username;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setIs_bot(boolean is_bot) {
        this.is_bot = is_bot;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
