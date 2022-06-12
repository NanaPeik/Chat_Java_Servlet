package ge.tsu.client;

import ge.tsu.model.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MessageSenderThread extends Thread {

    private final Socket clientSocket;
    private final Client client;

    public MessageSenderThread(Socket clientSocket, Client client) {
        this.clientSocket = clientSocket;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            var outputStream = clientSocket.getOutputStream();
            var objectOutputStream = new ObjectOutputStream(outputStream);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String text = scanner.nextLine();
                if (text.trim().equalsIgnoreCase("/exit")) {
                    break;
                }
                if (text != "")
                {
                    objectOutputStream.writeObject(new Message(client.getName(), text));
                    objectOutputStream.flush();
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
