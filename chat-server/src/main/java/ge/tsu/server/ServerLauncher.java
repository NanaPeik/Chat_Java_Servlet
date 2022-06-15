package ge.tsu.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerLauncher {
    private static final Logger log = LoggerFactory.getLogger(ServerLauncher.class);

    public static void main(String[] args) {
        log.info("Started application");
        try {
            Server server = new Server(1111);
            server.run();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            log.info("Finished application");
        }
    }
}
