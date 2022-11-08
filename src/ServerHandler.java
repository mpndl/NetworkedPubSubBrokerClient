import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ServerHandler {
    private final BlockingQueue<Message> serverMessages = new LinkedBlockingDeque<>();
    private final BlockingQueue<Message> subscribedMessages = new LinkedBlockingDeque<>();

    private ObjectOutputStream ous;
    private ObjectInputStream ois;
    private ServerReader serverReader;
    private ServerWriter serverWriter;
    private ConsoleWriter consoleWriter;

    public ServerHandler() {
        new ServerReader().start();
    }

    private class ServerWriter extends Thread {
        @Override
        public void run() {
            try {
                do {
                    Message msg = serverMessages.take();
                    ous.writeObject(msg);
                    ous.flush();
                }while (true);
            }catch (InterruptedException | IOException e) {
                e.printStackTrace();
                serverWriter = null;
            }
        }
    }

    private class ServerReader extends Thread{
        @Override
        public void run() {
            try {
                Socket connection = new Socket(InetAddress.getLocalHost(), 5050);
                ois = new ObjectInputStream(connection.getInputStream());
                ous = new ObjectOutputStream(connection.getOutputStream());
                ous.flush();

                serverWriter = new ServerWriter();
                serverWriter.start();

                consoleWriter = new ConsoleWriter();
                consoleWriter.start();

                Message msg;
                do {
                    msg = (Message) ois.readObject();
                    if (msg instanceof Publish)
                        subscribedMessages.add(msg);
                }while (!(msg instanceof Quit));

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }finally {
                serverReader = null;
                if (serverWriter != null) {
                    serverMessages.add(new Quit());
                }
                if (consoleWriter != null) consoleWriter.interrupt();
            }
        }
    }

    private class ConsoleWriter extends Thread {
        @Override
        public void run() {
            try {
                do {
                    Message msg = subscribedMessages.take();
                    System.out.println(">>> PUBLISHER SENT" + msg);
                }while (true);
            } catch (InterruptedException e) {
                e.printStackTrace();
                consoleWriter = null;
            }
        }
    }

    public void publish(String topic, String msg) {
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("message", msg);

        Message message = new Publish(null, topic, msgMap);
        serverMessages.add(message);
    }

    public void subscribe(String topic) {
        Message message = new Subscribe(topic, null);
        serverMessages.add(message);
    }

    public void quit() {
        serverMessages.add(new Quit());
    }
}
