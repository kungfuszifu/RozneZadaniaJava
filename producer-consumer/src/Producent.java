public class Producent implements Runnable {

    private SharedResource zadania;
    private SharedResource liczby;

    Producent(SharedResource zadania, SharedResource liczby) {
        this.zadania = zadania;
        this.liczby = liczby;
    }

    public void stop() {
        zadania.run = false;
        zadania.notifyFull();
        liczby.notifyEmpty();
    }

    @Override
    public void run() {
        produce();
    }

    public void produce() {
        while (zadania.run) {
            while (zadania.isFull() && zadania.run) {
                try {
                    zadania.waitOnFull();
                } catch (InterruptedException ex) {
                    break;
                }
            }

            while (liczby.isEmpty() && zadania.run) {
                try {
                    liczby.waitOnEmpty();
                } catch (InterruptedException ex) {
                    break;
                }
            }
            if (!zadania.run) {
                break;
            }

            Long liczba = liczby.Take();

            zadania.Add(liczba);
            zadania.notifyEmpty();
        }
    }
}
