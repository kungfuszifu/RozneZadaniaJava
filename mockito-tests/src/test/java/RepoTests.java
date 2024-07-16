import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RepoTests {

    @Test
    public void findTest_Brak() {
        MageRepository repository = new MageRepository();
        repository.save(new Mage(10, "Jacek"));
        repository.save(new Mage(5, "Radek"));

        Optional<Mage> odp = repository.find("Czarek");
        Optional<Mage> expected = Optional.empty();
        assertEquals(odp, expected, "Should be equal");
    }

    @Test
    public void findTest_Jest() {
        MageRepository repository = new MageRepository();
        repository.save(new Mage(10, "Jacek"));
        repository.save(new Mage(5, "Radek"));

        String odp = repository.find("Jacek").toString();
        String expected = Optional.of(new Mage(10, "Jacek")).toString();
        assertEquals(expected, odp, "Should be equal");
    }

    @Test
    public void deleteTest() {
        MageRepository repository = new MageRepository();
        repository.save(new Mage(10, "Jacek"));
        repository.save(new Mage(5, "Radek"));

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> repository.delete("Czarek")
        );

        assertEquals("Brak takiego elementu", thrown.getMessage());
    }

    @Test
    public void saveTest() {
        MageRepository repository = new MageRepository();
        repository.save(new Mage(10, "Jacek"));
        repository.save(new Mage(5, "Radek"));

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> repository.save(new Mage(10, "Jacek"))
        );

        assertEquals("Element juz istnieje", thrown.getMessage());
    }

}