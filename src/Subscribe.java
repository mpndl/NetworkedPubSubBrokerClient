import java.io.Serial;

public class Subscribe extends Message {
    @Serial
    private static final long serialVersionUID = 2L;

    private final String topic;
    public final Subscriber subscriber;

    public Subscribe(String topic, Subscriber subscriber) {
        this.topic = topic;
        this.subscriber = subscriber;
    }
}