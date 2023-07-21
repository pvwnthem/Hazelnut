import Client.Client;

public class RunClient {
    private static final Client client
            = new Client("10.0.0.45", 9000, "./out.txt");

    public static void main (String[] args) {
        client.Run();
    }
}
