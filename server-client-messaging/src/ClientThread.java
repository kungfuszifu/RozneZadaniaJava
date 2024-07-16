import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ClientThread implements Runnable {

    private Socket socket;

    ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream is = socket.getInputStream();
             OutputStream os = socket.getOutputStream()) {

            byte[] buffer = new byte[64];
            os.write("ready".getBytes());

            int length = is.read(buffer);
            int liczba = Integer.parseInt(readWiadomosc(buffer, length));

            Message[] messages = new Message[liczba];
            os.write("ready for messages".getBytes());

            ObjectInputStream ois = new ObjectInputStream(is);
            for (int i = 0; i < liczba; i++) {
                messages[i] = (Message) ois.readObject();
            }

            os.write("finished".getBytes());

            System.out.println("Klient z portu :" + socket.getPort());
            for (Message m : messages) {
                System.out.println(m.getNumber() + " : " + m.getContent());
            }
            System.out.println();

        }  catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String readWiadomosc(byte[] buffer, int length) {
        return new String(Arrays.copyOfRange(buffer, 0, length));
    }
}
