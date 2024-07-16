import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static public void stworzPlik() {
        File file = new File("src/wyniki.txt");
        try {
            if (!file.createNewFile()) {
                file.delete();
                file.createNewFile();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static public void wpiszLiczby(SharedResource resource) {
        try {
            String file = "src/liczby.txt";
            Scanner scanner = new Scanner(new File(file));

            while (scanner.hasNextLine()) {
                resource.Add(Long.parseLong(scanner.nextLine()));
            }

            scanner.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        int watki = 1;
        if (args.length > 0) {
            watki = Integer.parseInt(args[0]);
        }

        stworzPlik();

        SharedResource liczby = new SharedResource(0xffffff);
        SharedResource queue = new SharedResource(5);

        wpiszLiczby(liczby);

        Producent p = new Producent(queue, liczby);
        Consument c = new Consument(queue);

        for (int i = 0; i < watki; i++) {
            Thread prodthread = new Thread(p);
            Thread calcthread = new Thread(c);

            prodthread.start();
            calcthread.start();
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String com = scanner.nextLine();

            if (com.compareTo("stop") == 0) {
                p.stop();
                c.stop();
                System.out.println("Zakonczono program");
                break;
            }
            else {
                try {
                    Long liczba = Long.parseLong(com);
                    liczby.Add(liczba);
                    liczby.notifyEmpty();
                } catch (NumberFormatException ex) {
                    p.stop();
                    c.stop();
                    System.out.println("Nie podano liczby");
                    break;
                }
            }
        }
    }
}
