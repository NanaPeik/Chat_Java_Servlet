package ge.tsu.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class MessageReceiverThread extends Thread {

    private Socket clientSocket;

    public MessageReceiverThread(Socket clientSocket) {
        setDaemon(true);
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            var inputStream = clientSocket.getInputStream();
            var objectInputStream = new ObjectInputStream(inputStream);

            while (true) {
                System.out.println(objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
