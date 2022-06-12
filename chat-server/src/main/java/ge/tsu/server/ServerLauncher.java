package ge.tsu.server;

public class ServerLauncher {

    public static void main(String[] args) {
        System.out.println("Started application");
        try {
            Server server = new Server(1111);
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Finished application");
        }
    }
}
