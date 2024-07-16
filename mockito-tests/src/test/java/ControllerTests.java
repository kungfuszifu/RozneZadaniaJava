import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ControllerTests {

    @Test
    public void deleteTest_Brak() {
        MageRepository repo = mock(MageRepository.class);
        MageController controller = new MageController(repo);
        doThrow(new IllegalArgumentException(""))
                .when(repo)
                .delete(isA(String.class));

        String odp = controller.delete("Jacek");

        assertEquals("not found", odp);
    }

    @Test
    public void deleteTest_Jest() {
        MageRepository repo = mock(MageRepository.class);
        MageController controller = new MageController(repo);

        String odp = controller.delete("Jacek");

        assertEquals("done", odp);
    }

    @Test
    public void findTest_Brak() {
        MageRepository repo = mock(MageRepository.class);
        MageController controller = new MageController(repo);
        when(repo.find(isA(String.class))).thenReturn(Optional.empty());

        String odp = controller.find("Jacek");

        assertEquals("not found", odp);
    }

    @Test
    public void findTest_Jest() {
        MageRepository repo = mock(MageRepository.class);
        MageController controller = new MageController(repo);
        when(repo.find(isA(String.class))).thenReturn(Optional.of(new Mage(10, "Jacek")));

        String odp = controller.find("Jacek");

        assertEquals(new Mage(10, "Jacek").toString(), odp);
    }

    @Test
    public void saveTest_nonExistent() {
        MageRepository repo = mock(MageRepository.class);
        MageController controller = new MageController(repo);

        String odp = controller.save(10, "Jacek");

        assertEquals("done", odp);
    }

    @Test
    public void saveTest_Exists() {
        MageRepository repo = mock(MageRepository.class);
        MageController controller = new MageController(repo);
        doThrow(new IllegalArgumentException(""))
                .when(repo)
                .save(isA(Mage.class));

        String odp = controller.save(10, "Jacek");

        assertEquals("bad request", odp);
    }

}
