package cpm.web.dto.fb;

public class ChatDto {
    private float id;
    private String first_name;
    private String type;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getType() {
        return type;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setType(String type) {
        this.type = type;
    }
}
