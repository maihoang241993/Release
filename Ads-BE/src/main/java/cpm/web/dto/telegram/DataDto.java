package cpm.web.dto.telegram;

public class DataDto {
    private String id;
    private String role;
    private String email;
    private String invite_link;
    private String status;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getInvite_link() {
        return invite_link;
    }

    public String getStatus() {
        return status;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setInvite_link(String invite_link) {
        this.invite_link = invite_link;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
