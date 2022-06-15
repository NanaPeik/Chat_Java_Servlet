package ge.tsu.server;

import ge.tsu.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private Map<String, List<UserThread>> chatRooms = new HashMap<>();

    private int port;

    public Server() {
        port = 0;
    }

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // If port was auto-generated, update port number
            if (port == 0) {
                port = serverSocket.getLocalPort();
            }
            log.debug("Started server which listens on port number: {}", port);
            while (true) {
                log.info("Waiting for client...");
                Socket socket = serverSocket.accept();
                log.debug("Got new client: {}:{}",
                        socket.getInetAddress().getHostAddress(), socket.getPort());

                // Read chatroom name
                var inputStream = socket.getInputStream();
                var inputStreamReader = new InputStreamReader(inputStream);
                var bufferedReader = new BufferedReader(inputStreamReader);
                final String chatroom = bufferedReader.readLine();

                List<UserThread> userThreads = chatRooms.get(chatroom);
                if (userThreads == null) {
                    userThreads = new ArrayList<>();
                    chatRooms.put(chatroom, userThreads);
                }

                UserThread userThread = new UserThread(chatroom, socket, this);
                userThreads.add(userThread);
                userThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void broadCast(String chatroom, Message message, UserThread myself) {

        log.debug("Broadcasting line: {}", message);
        for (UserThread userThread : chatRooms.get(chatroom)) {
            if (!userThread.equals(myself)) {
                userThread.sendMessage(message);
            }
        }
    }

    public void removeUser(String chatroom, UserThread myself) {
        List<UserThread> users = chatRooms.get(chatroom);
        for (UserThread userThread : users) {
            if (userThread.equals(myself)) {
                users.remove(userThread);
                log.debug("User: {} leave the chat: {}", myself.getName(), chatroom);

                chatRooms.put(chatroom, users);
            }
        }
    }
}
