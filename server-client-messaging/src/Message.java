import java.io.Serializable;

public class Message implements Serializable {

    private int number;
    private String content;

    Message(int number, String content) {
        this.content = content;
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public int getNumber() {
        return number;
    }
}
