import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

public class MageRepository {
    private Collection<Mage> collection;

    public MageRepository() {
        this.collection = new LinkedList<>();
    }

    public Optional<Mage> find(String name) {
        for (Mage m : this.collection) {
            if (m.getName().equals(name)) {
                Optional<Mage> opt = Optional.of(m);
                return opt;
            }
        }
        Optional<Mage> opt = Optional.empty();
        return opt;
    }

    public void delete(String name) throws IllegalArgumentException {
        if (!this.collection.removeIf(m -> m.getName().equals(name))) {
            throw new IllegalArgumentException("Brak takiego elementu");
        }
    }

    public void save(Mage mage) throws IllegalArgumentException {
        for (Mage m: this.collection) {
            if (m.equals(mage)) {
                throw new IllegalArgumentException("Element juz istnieje");
            }
        }
        this.collection.add(mage);
    }
}
