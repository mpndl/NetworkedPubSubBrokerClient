import java.io.Serial;
import java.io.Serializable;

public abstract class Message<H> implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    public void apply(H handler) {}
}
