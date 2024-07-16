import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@NamedQueries ({
        @NamedQuery(name = "Piwo.findAll",
            query = "SELECT p FROM Piwo p")
})

@Entity
@Table(name="Mage")
public class Piwo {

    @Id
    @Column(name="name")
    private String name;

    private long cena;

    @ManyToOne
    private Browar browar;

    public Piwo() {}

    public Piwo(String name, long cena, Browar browar) {
        this.name = name;
        this.cena = cena;
        this.browar = browar;
    }

    public String getId() {return name;}
    public void setId(String name) {this.name = name;}

    public long getcena() {
        return cena;
    }

    public void setcena(long cena) {
        this.cena = cena;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String s = "Piwo{name="+name+", cena="+cena+", browar=";
        if (browar != null)
            s += browar.getName()+"}";
        else
            s += "_}";
        return s;
    }
}
