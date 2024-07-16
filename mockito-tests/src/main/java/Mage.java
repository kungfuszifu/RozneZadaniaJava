import java.util.Objects;

public class Mage {
    private String name;
    private int lvl;

    Mage(int lvl, String name) {
        this.lvl = lvl;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getLvl() {
        return lvl;
    }

    @Override
    public String toString() {
        return "Mage{" +
                "name='" + name + '\'' +
                ", lvl=" + lvl +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == this.getClass()) {
            Mage mage = (Mage) o;
            return this.getName().equals(mage.getName()) && this.getLvl() == mage.getLvl();
        }
        return false;
    }
}
