package Client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class Client {
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    private static Logger logger = Logger.getLogger("Client");

    private static String HOST;
    private static int PORT;
    private static String OUTPATH;

    public Client(String host, int port, String outPath)
    {
        HOST = host;
        PORT = port;
        OUTPATH=  outPath;
    }

    public void Run()
    {
        try (Socket socket = new Socket(HOST, PORT)) {

            dataInputStream = new DataInputStream(
                    socket.getInputStream());
            dataOutputStream = new DataOutputStream(
                    socket.getOutputStream());

            logger.info(
                    "Sending File to Server");

            send(
                    OUTPATH);

            dataInputStream.close();
            dataOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void send(String path) {
        try {
            int bytes = 0;

            File file = new File(path);
            FileInputStream fileInputStream
                    = new FileInputStream(file);

            dataOutputStream.writeLong(file.length());

            byte[] buf = new byte[4 * 1024];
            while ((bytes = fileInputStream.read(buf))
                    != -1) {
                dataOutputStream.write(buf, 0, bytes);
                dataOutputStream.flush();
            }

            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
