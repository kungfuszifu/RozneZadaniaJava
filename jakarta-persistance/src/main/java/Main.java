import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static EntityManagerFactory factory = Persistence.createEntityManagerFactory("rpgPu");

    public static void main(String[] args) {

        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        Browar b1 = new Browar("Browar1", 23);
        Browar b2 = new Browar("Browar2", 38);

        Piwo p1 = new Piwo("A", 5, b1);
        Piwo p2 = new Piwo("B", 13, b1);
        Piwo p3 = new Piwo("C", 2, b2);

        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(b1);
        em.persist(b2);

        em.getTransaction().commit();
        em.close();

        Scanner s = new Scanner(System.in);
        while (true) {
            String line = s.nextLine();
            if (line.split(" ")[0].compareTo("delete") == 0) {
                String[] param = line.split(" ");
                if (param[1].compareTo("piwo") == 0)
                    removePiwo(param[2]);
                else if (param[1].compareTo("browar") == 0) {
                    removeBrowar(param[2]);
                }

            } else if (line.split(" ")[0].compareTo("add") == 0) {
                addItem(line);
            } else if (line.split(" ")[0].compareTo("query")==0) {
                queryItems(line);
            } else if (line.compareTo("print")==0) {
                printAll();
            } else if (line.compareTo("quit")==0) {
                break;
            }
        }

        factory.close();
    }

    public static void printAll() {
        EntityManager em = factory.createEntityManager();

        Query query = em.createNamedQuery("Browar.findAll");
        List<Browar> piwa = query.getResultList();
        for (Browar p:piwa) {
            System.out.println(p);
        }

        query = em.createNamedQuery("Piwo.findAll");
        List<Piwo> p = query.getResultList();
        for (Piwo pp:p) {
            System.out.println(pp);
        }

        em.close();
    }

    public static void queryItems(String line) {
        String[] param = line.split(" ");

        EntityManager em = factory.createEntityManager();
        Query query = null;
        if (param[1].compareTo("1")==0) {
            query = em.createQuery("Select p from Piwo p where p.cena < :cena", Piwo.class);
            query.setParameter("cena", param[2]);

        } else if (param[1].compareTo("2")==0) {
            query = em.createQuery("Select p from Piwo p where p.cena > :cena and p.browar.name like :name", Piwo.class);
            query.setParameter("cena", param[3]);
            query.setParameter("name", param[2]);
        }
        List<Piwo> items = query.getResultList();
        for (Piwo p:items) {
            System.out.println(p);
        }
        em.close();
    }

    public static void addItem(String line) {
        Object o = new Object();
        String[] param = line.split(" ");

        if (param[1].compareTo("piwo") == 0) {
            EntityManager em = factory.createEntityManager();
            Query query = em.createQuery("Select b from Browar b where b.name like :name", Browar.class);
            query.setParameter("name", param[4]);
            List<Browar> b = query.getResultList();
            em.close();

            Browar br = null;
            if (b.size() > 0)
                br = b.get(0);

            o = new Piwo(param[2], Long.parseLong(param[3]), br);
        } else if (param[1].compareTo("browar") == 0) {
            o = new Browar(param[2], Long.parseLong(param[3]));
        }

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
    }

    public static void removePiwo(String name) {
        try { EntityManager em = factory.createEntityManager();
            em.getTransaction().begin();
            Piwo piwo = em.find(Piwo.class, name);
            em.remove(piwo);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeBrowar(String name) {
        try { EntityManager em = factory.createEntityManager();
            em.getTransaction().begin();
            Browar browar = em.find(Browar.class, name);
            em.remove(browar);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
