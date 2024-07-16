import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Consument implements Runnable {

    private SharedResource zadania;
    static private Object plik = new Object();

    Consument(SharedResource zadania) {
        this.zadania = zadania;
    }

    public void stop() {
        zadania.run = false;
        zadania.notifyEmpty();
    }

    @Override
    public void run() {
        calculate();
    }

    public void calculate() {
        while (zadania.run) {
            while (zadania.isEmpty() && zadania.run) {
                try {
                    zadania.waitOnEmpty();
                } catch (InterruptedException ex) {
                    break;
                }
            }
            if (!zadania.run) {
                break;
            }

            Long liczba = zadania.Take();
            zadania.notifyFull();
            obliczDzielniki(liczba);
        }
    }

    public void obliczDzielniki(Long liczba) {
        List<Long> dzielniki = new LinkedList<>();

        for (Long i = Long.valueOf("2"); i < liczba; i++) {
            if (liczba % i == 0)
                dzielniki.add(i);
        }
        if (liczba < 10000)
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {

            }

        File file = new File("src/wyniki.txt");
        synchronized (plik) {
            try {
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bw = new BufferedWriter(fw);

                if (dzielniki.size() > 0 || liczba <= 1) {
                    bw.write("liczba: " + liczba);
                    for (Long d : dzielniki) {
                        bw.write(", dzielnik: " + d);
                    }
                    bw.write("\n");
                } else {
                    bw.write("liczba: " + liczba + " jest pierwsza");
                    bw.write("\n");
                }
                bw.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
