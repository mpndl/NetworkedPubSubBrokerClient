import java.io.Serial;

public class Unsubscribe extends Message {
    @Serial
    private static final long serialVersionUID = 3L;

    private final String topic;
    private final Subscriber subscriber;

    public Unsubscribe(String topic, Subscriber subscriber) {
        this.topic = topic;
        this.subscriber = subscriber;
    }
}