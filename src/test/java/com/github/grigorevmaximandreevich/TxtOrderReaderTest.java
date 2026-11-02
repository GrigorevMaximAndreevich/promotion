package com.github.grigorevmaximandreevich;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TxtOrderReaderTest {

    @TempDir
    Path tempDir;

    @Test
    void isSupported_shouldReturnTrueForTxtFiles() {
        TxtOrderReader reader = new TxtOrderReader();

        assertTrue(reader.isSupported("data.txt"));
        assertTrue(reader.isSupported("/path/to/file.txt"));
    }

    @Test
    void isSupported_shouldReturnFalseForNonTxtFiles() {
        TxtOrderReader reader = new TxtOrderReader();

        assertFalse(reader.isSupported("data.csv"));
        assertFalse(reader.isSupported("data"));
    }

    @Test
    void read_shouldParseFileWithPipeDelimiter() throws IOException {
        Path testFile = tempDir.resolve("test.txt");
        String fileContent = """
                2021-02-09T16:00:22|Industrial|8800
                2021-02-09T08:42:59|Power Engineer|17480
                2021-02-09T08:19:22|Recovery|11340
                """;
        Files.writeString(testFile, fileContent);

        TxtOrderReader reader = new TxtOrderReader();
        List<Order> orders = reader.read(testFile.toString());

        assertEquals(3, orders.size());
        assertEquals("Industrial", orders.get(0).getCompanyName());
        assertEquals(8800, orders.get(0).getQuantity());
        assertEquals("Power Engineer", orders.get(1).getCompanyName());
        assertEquals("Recovery", orders.get(2).getCompanyName());
    }

    @Test
    void read_shouldHandleEmptyLines() throws IOException {
        Path testFile = tempDir.resolve("test.txt");
        String fileContent = """
                2021-02-09T08:00:00|Company A|1000
                
                2021-02-09T09:00:00|Company B|2000
                """;
        Files.writeString(testFile, fileContent);

        TxtOrderReader reader = new TxtOrderReader();
        List<Order> orders = reader.read(testFile.toString());

        assertEquals(2, orders.size());
    }

    @Test
    void read_shouldThrowExceptionOnMalformedLine() throws IOException {
        Path testFile = tempDir.resolve("test.txt");
        Files.writeString(testFile, "2021-02-09T08:00:00|Company\n");

        TxtOrderReader reader = new TxtOrderReader();
        assertThrows(RuntimeException.class, () -> reader.read(testFile.toString()));
    }

}
