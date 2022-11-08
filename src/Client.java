import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        new Client();
    }

    private ServerHandler serverHandler;
    Client() {
        serverHandler = new ServerHandler();
        menu();
    }

    private void menu() {
        while (true) {
            System.out.println("PubSubClient:");
            System.out.println("1. Publish");
            System.out.println("2. Subscribe");
            System.out.println("3. Quit");
            System.out.print("> ");

            Scanner scanner = new Scanner(System.in);
            int in = scanner.nextInt();

            if (in == 1)
                publish();
            else if (in == 2)
                subscribe();
            else {
                serverHandler.quit();
                System.exit(0);
            }
        }
    }

    private void subscribe() {
        System.out.println("Topic: ");
        Scanner scanner = new Scanner(System.in);
        String topic = scanner.nextLine();

        serverHandler.subscribe(topic);
    }

    private void publish() {
        System.out.println("Topic: ");
        Scanner scanner = new Scanner(System.in);
        String topic = scanner.nextLine();

        System.out.println("Message: ");
        String msg = scanner.nextLine();

        serverHandler.publish(topic, msg);
    }
}
