import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8080)) {
            server.setSoTimeout(10000);

            while (true) {
                try {
                    Socket socket = server.accept();

                    ClientThread client = new ClientThread(socket);
                    new Thread(client).start();

                } catch (InterruptedIOException ex) {
                    System.out.println("No client has connected in 10 seconds do you wish to stop the server (y/n)");
                    Scanner scanner = new Scanner(System.in);
                    if (scanner.nextLine().compareTo("y") == 0) {
                        break;
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
