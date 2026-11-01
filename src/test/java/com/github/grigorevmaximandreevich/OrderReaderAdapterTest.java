package com.github.grigorevmaximandreevich;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderReaderAdapterTest {

    @TempDir
    Path tempDir;

    @Test
    void constructor_shouldRejectEmptyDelimiters() {
        assertThrows(RuntimeException.class, () -> new OrderReaderAdapter(null));
        assertThrows(RuntimeException.class, () -> new OrderReaderAdapter(""));
    }

    @Test
    void constructor_shouldAcceptValidDelimiters() {
        assertDoesNotThrow(() -> new OrderReaderAdapter("|"));
        assertDoesNotThrow(() -> new OrderReaderAdapter("#"));
    }

    @Test
    void read_shouldParseRealDataCorrectly() throws IOException {
        Path testFile = tempDir.resolve("discount_day.txt");
        String fileContent = """
                2021-02-09T16:00:22|Industrial|8800
                2021-02-09T08:42:59|Power Engineer|17480
                2021-02-09T08:19:22|Recovery|11340
                """;
        Files.writeString(testFile, fileContent);

        OrderReaderAdapter adapter = new OrderReaderAdapter("|");
        List<Order> orders = adapter.read(testFile.toString());

        assertEquals("Industrial", orders.get(0).getCompanyName());
        assertEquals("Power Engineer", orders.get(1).getCompanyName());
        assertEquals("Recovery", orders.get(2).getCompanyName());
    }



}
