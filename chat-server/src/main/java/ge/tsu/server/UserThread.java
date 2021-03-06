package ge.tsu.server;

import ge.tsu.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class UserThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(UserThread.class);

    private final String chatroom;
    private final Socket socket;
    private final Server server;
    private final ObjectOutputStream objectOutputStream;

    public UserThread(String chatroom, Socket socket, Server server) {
        this.chatroom = chatroom;
        this.socket = socket;
        this.server = server;
        try {
            OutputStream outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            while (true) {
                if (objectInputStream != null) {
                    var message = (Message) objectInputStream.readObject();
                    if (!message.getText().equals("/exit"))
                        server.broadCast(chatroom, message, this);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            server.removeUser(chatroom, this);
            log.warn(e.getMessage());
        }
    }

    public void sendMessage(Message message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {
//            throw new RuntimeException(e);
            log.error(e.getMessage());
        }
    }
}
