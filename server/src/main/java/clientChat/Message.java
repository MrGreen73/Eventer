package clientChat;

import lombok.Data;

@Data
public class Message {
    private String content;
    private Long id = 3L;

    public Message() {
    }

    public Message(String content) {
        this.content = content;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
