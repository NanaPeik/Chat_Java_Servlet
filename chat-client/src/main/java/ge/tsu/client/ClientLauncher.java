package ge.tsu.client;

public class ClientLauncher {

    public static void main(String[] args) {
        Client client = new Client("localhost", 1111);
        client.run();
    }
}
