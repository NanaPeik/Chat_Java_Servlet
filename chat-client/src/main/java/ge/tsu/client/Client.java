package ge.tsu.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    private final String host;
    private final int port;
    private String name;
    private String chatroom;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter chatroom name: ");
            chatroom = scanner.nextLine();

            System.out.print("Enter your name: ");
            name = scanner.nextLine();

            Socket clientSocket = new Socket(host, port);
            var outputStream = clientSocket.getOutputStream();
            var printStream = new PrintStream(outputStream, true);
            printStream.println(chatroom);

            new MessageReceiverThread(clientSocket).start();
            new MessageSenderThread(clientSocket, this).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }
}
