package com.github.grigorevmaximandreevich;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NoExtOrderReaderTest {

    @TempDir
    Path tempDir;

    @Test
    void isSupported_shouldReturnTrueForFilesWithoutExtension() {
        NoExtOrderReader reader = new NoExtOrderReader();

        assertTrue(reader.isSupported("data_file"));
        assertTrue(reader.isSupported("discount_day_without_ext"));
    }

    @Test
    void isSupported_shouldReturnFalseForFilesWithExtension() {
        NoExtOrderReader reader = new NoExtOrderReader();

        assertFalse(reader.isSupported("data.txt"));
        assertFalse(reader.isSupported("data.csv"));
    }

    @Test
    void read_shouldParseFileWithHashDelimiter() throws IOException {
        Path testFile = tempDir.resolve("test_no_ext");
        String fileContent = """
                2021-02-09T16:00:22#Industrial#8800
                2021-02-09T08:42:59#Power Engineer#17480
                2021-02-09T08:19:22#Recovery#11340
                """;
        Files.writeString(testFile, fileContent);

        NoExtOrderReader reader = new NoExtOrderReader();
        List<Order> orders = reader.read(testFile.toString());

        assertEquals(3, orders.size());
        assertEquals("Industrial", orders.get(0).getCompanyName());
        assertEquals(8800, orders.get(0).getQuantity());
        assertEquals("Power Engineer", orders.get(1).getCompanyName());
        assertEquals("Recovery", orders.get(2).getCompanyName());
    }

    @Test
    void read_shouldHandleEmptyLines() throws IOException {
        Path testFile = tempDir.resolve("test_no_ext");
        String fileContent = """
                2021-02-09T08:00:00#Company A#1000
                
                2021-02-09T09:00:00#Company B#2000
                """;
        Files.writeString(testFile, fileContent);

        NoExtOrderReader reader = new NoExtOrderReader();
        List<Order> orders = reader.read(testFile.toString());

        assertEquals(2, orders.size());
    }
}
