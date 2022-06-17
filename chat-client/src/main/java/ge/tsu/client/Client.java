package ge.tsu.client;

import ge.tsu.model.repository.ClientRepository;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    private final String host;
    private final int port;
    private String name;
    ClientRepository repository = new ClientRepository();

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter chatroom name: ");
            String chatroom = scanner.nextLine();

            System.out.print("Enter your name: ");
            name = scanner.nextLine();
            repository.addClient(new ge.tsu.model.Client(name));

            Socket clientSocket = new Socket(host, port);
            var outputStream = clientSocket.getOutputStream();
            var printStream = new PrintStream(outputStream, true);
            printStream.println(chatroom);

            new MessageReceiverThread(clientSocket).start();
            new MessageSenderThread(clientSocket, this).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }
}
