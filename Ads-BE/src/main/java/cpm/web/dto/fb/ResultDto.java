package cpm.web.dto.fb;

public class ResultDto {
    private float message_id;
    FromDto FromObject;
    ChatDto ChatObject;
    private float date;
    private String text;


    // Getter Methods

    public float getMessage_id() {
        return message_id;
    }

    public FromDto getFromDto() {
        return FromObject;
    }

    public ChatDto getChatDto() {
        return ChatObject;
    }

    public float getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    // Setter Methods

    public void setMessage_id(float message_id) {
        this.message_id = message_id;
    }

    public void setFromDto(FromDto FromDtoObject) {
        this.FromObject = FromDtoObject;
    }

    public void setChatDto(ChatDto ChatDtoObject) {
        this.ChatObject = ChatDtoObject;
    }

    public void setDate(float date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }
}
