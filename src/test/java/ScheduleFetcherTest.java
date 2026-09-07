import com.eternaltempted.service.ScheduleFetcher;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ScheduleFetcherTest {

    public ScheduleFetcher fetcher = new ScheduleFetcher();

    @Test
    public void groupMissingIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                fetcher.fetch(
                        "",
                        "446469",
                        2
                )
        );

        assertEquals("Group cannot be null or empty", exception.getMessage());
    }

    @Test
    public void studentMissingIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                fetcher.fetch(
                        "446469",
                        "",
                        2
                )
        );

        assertEquals("Student cannot be null or empty", exception.getMessage());
    }

    @Test
    public void invalidWeekIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                fetcher.fetch(
                        "38356",
                        "446469",
                        54
                )
        );

        assertEquals("The week should be between 1 and 52, got: " + 54, exception.getMessage());
    }

    @Test
    public void returnsDocumentSuccessfully() throws IOException {
        Document doc = fetcher.fetch(
                "38354",
                "446469",
                2
        );

        assertNotNull(doc);
        assertFalse(doc.body().text().isEmpty());
    }
}
