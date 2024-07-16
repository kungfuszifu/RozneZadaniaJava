import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try (Socket client = new Socket("localhost", 8080)) {
            try (OutputStream os = client.getOutputStream();
                 InputStream is = client.getInputStream()) {

                byte[] buffer = new byte[64];
                Scanner scanner = new Scanner(System.in);


                int length = is.read(buffer);
                String wiadomosc = readWiadomosc(buffer, length);

                String n = "";
                if (wiadomosc.compareTo("ready") == 0) {
                    System.out.println("Server is ready");
                    System.out.println("Podaj ilosc wiadomosci do wyslania:");
                    n = scanner.nextLine();
                    os.write(n.getBytes());
                }

                length = is.read(buffer);
                wiadomosc = readWiadomosc(buffer, length);

                if (wiadomosc.compareTo("ready for messages") == 0) {
                    System.out.println("Server ready for messages");
                    int intN = Integer.parseInt(n);
                    ObjectOutputStream oos = new ObjectOutputStream(os);

                    Random r = new Random();
                    for (int i=0; i<intN; i++) {
                        Message message = new Message(i, "Jakis tekst "+r.nextInt(100));
                        oos.writeObject(message);
                    }
                }

                length = is.read(buffer);
                wiadomosc = readWiadomosc(buffer, length);

                if (wiadomosc.compareTo("finished") == 0) {
                    System.out.println("Success");
                }
                else {
                    System.out.println("Failure");
                }

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String readWiadomosc(byte[] buffer, int length) {
        return new String(Arrays.copyOfRange(buffer, 0, length));
    }
}
