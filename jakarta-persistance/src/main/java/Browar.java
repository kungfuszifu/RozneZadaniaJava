import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;


@NamedQueries({
        @NamedQuery(name = "Browar.findAll",
            query = "SELECT b FROM Browar b")
})

@Entity
@Table(name = "name")
public class Browar {

    @Id
    @Column(name = "name")
    private String name;

    private long wartosc;

    @OneToMany(mappedBy = "browar", cascade = CascadeType.REMOVE)
    private List<Piwo> piwa;

    public Browar() {}

    public Browar(String name, long wartosc) {
        this.name = name;
        this.wartosc = wartosc;
        this.piwa = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getwartosc() {
        return wartosc;
    }

    public void setwartosc(long wartosc) {
        this.wartosc = wartosc;
    }

    public List<Piwo> getPiwa() {
        return piwa;
    }

    public void removePiwo(Piwo piwo) {
        piwa.remove(piwo);
    }

    @Override
    public String toString() {
        String s = "Browar (name="+name+", wartosc="+wartosc+", piwa:\n";
        for (Piwo p: piwa) {
            s += ("    "+p.toString()+"\n");
        }
        s += (")\n");
        return s;
    }
}
