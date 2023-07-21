package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server {
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    private static int PORT;
    private static String FILENAME;

    private static Logger logger = Logger.getLogger("Server");

    public Server(int port, String fileName)
    {
        PORT = port;
        FILENAME = fileName;
    }
    public static void Run()
    {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info(String.format("Server starting on port %s", PORT));

            Socket clientSocket = serverSocket.accept();
            logger.info(
                    "Successfully connected");

            dataInputStream = new DataInputStream(
                    clientSocket.getInputStream());

            dataOutputStream = new DataOutputStream(
                    clientSocket.getOutputStream());

            recv(FILENAME);

            dataInputStream.close();
            dataOutputStream.close();
            clientSocket.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void recv(String fileName) {
        try {
            int bytes = 0;
            FileOutputStream fileOutputStream
                    = new FileOutputStream(fileName);

            long size
                    = dataInputStream.readLong();

            byte[] buf = new byte[4 * 1024];

            while (size > 0
                    && (bytes = dataInputStream.read(
                            buf, 0,
                            (int) Math.min(buf.length, size)))
                            != -1) {
                fileOutputStream.write(buf, 0, bytes);
                size -= bytes;
            }

            logger.info(
                    "File Recieved");
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
