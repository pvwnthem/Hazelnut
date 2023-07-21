import Server.Server;

public class RunServer {
    private static final Server server
            = new Server(9000, "out.txt");

    public static void main (String[] args) {
        server.Run();
    }
}
