import java.util.Optional;

public class MageController {
    private MageRepository repository;

    MageController(MageRepository repository) {
        this.repository = repository;
    }

    public String find(String name) {
        Optional<Mage> opt = repository.find(name);
        if (opt.isPresent()) {
            return opt.get().toString();
        }
        else {
            return "not found";
        }
    }

    public String delete(String name) {
        try {
            repository.delete(name);
        } catch (IllegalArgumentException ex) {
            return "not found";
        }
        return "done";
    }

    public String save(int level, String name) {
        Mage mage = new Mage(level, name);
        try {
            repository.save(mage);
        } catch (IllegalArgumentException ex) {
            return "bad request";
        }
        return "done";
    }
}
