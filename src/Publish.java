import java.io.Serial;
import java.util.Map;

public class Publish extends Message{
    @Serial
    private static final long serialVersionUID = 1L;

    private final Object publisher;
    private final String topic;
    private final Map<String,Object> params;

    public Publish(Object publisher, String topic, Map<String, Object> params) {
        this.publisher = publisher;
        this.topic = topic;
        this.params = params;
    }

    @Override
    public String toString() {
        return "Publish{" +
                "publisher=" + publisher +
                ", topic='" + topic + '\'' +
                ", params=" + params.get("message") +
                '}';
    }
}
